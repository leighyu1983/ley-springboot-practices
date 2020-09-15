
// https://www.cnblogs.com/trust-freedom/p/6349502.html

//package com.ley.source.httpclient;
//
///*
// * ====================================================================
// * Licensed to the Apache Software Foundation (ASF) under one
// * or more contributor license agreements.  See the NOTICE file
// * distributed with this work for additional information
// * regarding copyright ownership.  The ASF licenses this file
// * to you under the Apache License, Version 2.0 (the
// * "License"); you may not use this file except in compliance
// * with the License.  You may obtain a copy of the License at
// *
// *   http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing,
// * software distributed under the License is distributed on an
// * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// * KIND, either express or implied.  See the License for the
// * specific language governing permissions and limitations
// * under the License.
// * ====================================================================
// *
// * This software consists of voluntary contributions made by many
// * individuals on behalf of the Apache Software Foundation.  For more
// * information on the Apache Software Foundation, please see
// * <http://www.apache.org/>.
// *
// */
//package org.apache.http.pool;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.CancellationException;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//import org.apache.http.annotation.Contract;
//import org.apache.http.annotation.ThreadingBehavior;
//import org.apache.http.concurrent.FutureCallback;
//import org.apache.http.util.Args;
//import org.apache.http.util.Asserts;
//
///**
// * Abstract synchronous (blocking) pool of connections.
// * <p>
// * Please note that this class does not maintain its own pool of execution {@link Thread}s.
// * Therefore, one <b>must</b> call {@link Future#get()} or {@link Future#get(long, TimeUnit)}
// * method on the {@link Future} object returned by the
// * {@link #lease(Object, Object, FutureCallback)} method in order for the lease operation
// * to complete.
// *
// * @param <T> the route type that represents the opposite endpoint of a pooled
// *   connection.
// * @param <C> the connection type.
// * @param <E> the type of the pool entry containing a pooled connection.
// * @since 4.2
// */
//@Contract(threading = ThreadingBehavior.SAFE_CONDITIONAL)
//public abstract class AbstractConnPool<T, C, E extends PoolEntry<T, C>>
//		implements ConnPool<T, E>, ConnPoolControl<T> {
//
//	private final Lock lock;
//	private final Condition condition;
//	private final ConnFactory<T, C> connFactory;
//	// 全局 map<route, RouteSpecificPool>
//	private final Map<T, RouteSpecificPool<T, C, E>> routeToPool;
//	//------------  available + leased <= max
//	// 全局 租用中连接 CPoolEntry
//	private final Set<E> leased;
//	// 全局 可用连接 CPoolEntry
//	private final LinkedList<E> available;
//	// 全局 等待处理的http请求，比如10个请求，maxPerRoute是2，即当前leased=2，则pending=8
//	private final LinkedList<Future<E>> pending;
//	private final Map<T, Integer> maxPerRoute;
//
//	private volatile boolean isShutDown;
//	private volatile int defaultMaxPerRoute;
//	private volatile int maxTotal;
//	private volatile int validateAfterInactivity;
//
//	public AbstractConnPool(
//			final ConnFactory<T, C> connFactory,
//			final int defaultMaxPerRoute,
//			final int maxTotal) {
//		super();
//		this.connFactory = Args.notNull(connFactory, "Connection factory");
//		this.defaultMaxPerRoute = Args.positive(defaultMaxPerRoute, "Max per route value");
//		this.maxTotal = Args.positive(maxTotal, "Max total value");
//		this.lock = new ReentrantLock();
//		this.condition = this.lock.newCondition();
//		this.routeToPool = new HashMap<T, RouteSpecificPool<T, C, E>>();
//		this.leased = new HashSet<E>();
//		this.available = new LinkedList<E>();
//		this.pending = new LinkedList<Future<E>>();
//		this.maxPerRoute = new HashMap<T, Integer>();
//	}
//
//	/**
//	 * Creates a new entry for the given connection with the given route.
//	 */
//	protected abstract E createEntry(T route, C conn);
//
//	/**
//	 * @since 4.3
//	 */
//	protected void onLease(final E entry) {
//	}
//
//	/**
//	 * @since 4.3
//	 */
//	protected void onRelease(final E entry) {
//	}
//
//	/**
//	 * @since 4.4
//	 */
//	protected void onReuse(final E entry) {
//	}
//
//	/**
//	 * @since 4.4
//	 */
//	protected boolean validate(final E entry) {
//		return true;
//	}
//
//	public boolean isShutdown() {
//		return this.isShutDown;
//	}
//
//	/**
//	 * Shuts down the pool.
//	 */
//	public void shutdown() throws IOException {
//		if (this.isShutDown) {
//			return ;
//		}
//		this.isShutDown = true;
//		this.lock.lock();
//		try {
//			for (final E entry: this.available) {
//				entry.close();
//			}
//			for (final E entry: this.leased) {
//				entry.close();
//			}
//			for (final RouteSpecificPool<T, C, E> pool: this.routeToPool.values()) {
//				pool.shutdown();
//			}
//			this.routeToPool.clear();
//			this.leased.clear();
//			this.available.clear();
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	// getSet 获取or创建并返回 route
//	private RouteSpecificPool<T, C, E> getPool(final T route) {
//		RouteSpecificPool<T, C, E> pool = this.routeToPool.get(route);
//		if (pool == null) {
//			pool = new RouteSpecificPool<T, C, E>(route) {
//
//				@Override
//				protected E createEntry(final C conn) {
//					return AbstractConnPool.this.createEntry(route, conn);
//				}
//
//			};
//			this.routeToPool.put(route, pool);
//		}
//		return pool;
//	}
//
//	private static Exception operationAborted() {
//		return new CancellationException("Operation aborted");
//	}
//
//	/**
//	 * 创建连接时 PoolingHttpClientConnectionManager -》requestConnection
//	 * 用于返回Future，但是并没有启动。
//	 *
//	 */
//	@Override
//	public Future<E> lease(final T route, final Object state, final FutureCallback<E> callback) {
//		Args.notNull(route, "Route");
//		Asserts.check(!this.isShutDown, "Connection pool shut down");
//
//		return new Future<E>() {
//
//			private final AtomicBoolean cancelled = new AtomicBoolean(false);
//			private final AtomicBoolean done = new AtomicBoolean(false);
//			private final AtomicReference<E> entryRef = new AtomicReference<E>(null);
//
//			@Override
//			public boolean cancel(final boolean mayInterruptIfRunning) {
//				if (done.compareAndSet(false, true)) {
//					cancelled.set(true);
//					lock.lock();
//					try {
//						condition.signalAll();
//					} finally {
//						lock.unlock();
//					}
//					if (callback != null) {
//						callback.cancelled();
//					}
//					return true;
//				}
//				return false;
//			}
//
//			@Override
//			public boolean isCancelled() {
//				return cancelled.get();
//			}
//
//			@Override
//			public boolean isDone() {
//				return done.get();
//			}
//
//			@Override
//			public E get() throws InterruptedException, ExecutionException {
//				try {
//					return get(0L, TimeUnit.MILLISECONDS);
//				} catch (final TimeoutException ex) {
//					throw new ExecutionException(ex);
//				}
//			}
//
//			//
//			// 从连接池获取连接入口， 创建连接的时候会调用此处。启动阻塞线程从连接池获取连接。
//			//
//			@Override
//			public E get(final long timeout, final TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
//				for (;;) {
//					//
//					synchronized (this) {
//						try {
//							final E entry = entryRef.get();
//							if (entry != null) {
//								return entry;
//							}
//							if (done.get()) {
//								throw new ExecutionException(operationAborted());
//							}
//							// 从阻塞链表中获取可用连接
//							final E leasedEntry = getPoolEntryBlocking(route, state, timeout, timeUnit, this);
//							if (validateAfterInactivity > 0)  {
//								if (leasedEntry.getUpdated() + validateAfterInactivity <= System.currentTimeMillis()) {
//									if (!validate(leasedEntry)) {
//										leasedEntry.close();
//										release(leasedEntry, false);
//										continue;
//									}
//								}
//							}
//							if (done.compareAndSet(false, true)) {
//								entryRef.set(leasedEntry);
//								done.set(true);
//								onLease(leasedEntry);
//								if (callback != null) {
//									callback.completed(leasedEntry);
//								}
//								return leasedEntry;
//							} else {
//								release(leasedEntry, true);
//								throw new ExecutionException(operationAborted());
//							}
//						} catch (final IOException ex) {
//							if (done.compareAndSet(false, true)) {
//								if (callback != null) {
//									callback.failed(ex);
//								}
//							}
//							throw new ExecutionException(ex);
//						}
//					}
//				}
//			}
//
//		};
//	}
//
//	/**
//	 * Attempts to lease a connection for the given route and with the given
//	 * state from the pool.
//	 * <p>
//	 * Please note that this class does not maintain its own pool of execution
//	 * {@link Thread}s. Therefore, one <b>must</b> call {@link Future#get()}
//	 * or {@link Future#get(long, TimeUnit)} method on the {@link Future}
//	 * returned by this method in order for the lease operation to complete.
//	 *
//	 * @param route route of the connection.
//	 * @param state arbitrary object that represents a particular state
//	 *  (usually a security principal or a unique token identifying
//	 *  the user whose credentials have been used while establishing the connection).
//	 *  May be {@code null}.
//	 * @return future for a leased pool entry.
//	 */
//	public Future<E> lease(final T route, final Object state) {
//		return lease(route, state, null);
//	}
//
//	/**
//	 获取连接超时，TimeoutException
//
//	 */
//	private E getPoolEntryBlocking(
//			final T route, final Object state,
//			final long timeout, final TimeUnit timeUnit,
//			final Future<E> future) throws IOException, InterruptedException, ExecutionException, TimeoutException {
//
//		Date deadline = null;
//		// --------------------------------
//		// timeout: 从connectionManager连接池获取连接超时时间
//		if (timeout > 0) {
//			deadline = new Date (System.currentTimeMillis() + timeUnit.toMillis(timeout));
//		}
//		this.lock.lock();
//		try {
//			// 每个route对应一个连接池
//			final RouteSpecificPool<T, C, E> pool = getPool(route);
//			E entry;
//			for (;;) {
//				Asserts.check(!this.isShutDown, "Connection pool shut down");
//				if (future.isCancelled()) {
//					throw new ExecutionException(operationAborted());
//				}
//				// ----------------- available 有可用连接 1/2---------------
//				// ....类似redis get时候判断key是否过期....
//				// 如果连接池有可用连接，该连接从route available集合转移到leased结合;
//				// 判断连接是否为过期or关闭状态，符合条件就释放，进行下一轮判断;
//				// 如果连接池没有，到下面去创建。
//				for (;;) {
//					// 从route leased链表中获取连接
//					entry = pool.getFree(state);
//					// 没有可用连接，退出去尝试创建
//					if (entry == null) {
//						break;
//					}
//					// ----- 有可用连接
//					// 连接过期则关闭
//					if (entry.isExpired(System.currentTimeMillis())) {
//						entry.close();
//					}
//					// 连接已关闭，从available链表删除，从lease链表删除
//					if (entry.isClosed()) {
//						this.available.remove(entry);
//						// 从route lease链表删除该连接
//						// 第二个参数表示是否可以服用，如果是true,将连接放回available链表
//						pool.free(entry, false);
//					} else {
//						break;
//					}
//				}
//				// ----------------- available 有可用连接 2/2---------------
//				// 配合上一步，如果有可用连接，该连接从全局 available集合转移到leased结合
//				// 返回连接
//				if (entry != null) {
//					this.available.remove(entry);
//					this.leased.add(entry);
//					onReuse(entry);
//					return entry;
//				}
//
//				// -------------
//				final int maxPerRoute = getMax(route);
//				// route getAllocatedCount = available + leased
//				final int excess = Math.max(0, pool.getAllocatedCount() + 1 - maxPerRoute);
//				//------------------- 针对当前route，关闭超出部分的连接
//				// 如果route的 available + leased > max per route,
//				// 关闭超出部分的连接
//				// 这些连接从全局available和route的available和leased中删除
//				if (excess > 0) {
//					for (int i = 0; i < excess; i++) {
//						// 获取链表available的最后一个item
//						final E lastUsed = pool.getLastUsed();
//						// 如果没有直接退出
//						if (lastUsed == null) {
//							break;
//						}
//						// 关闭连接
//						lastUsed.close();
//						// 从全局available链表删除
//						this.available.remove(lastUsed);
//						// 从route的available链表和leased链表删除该连接entry
//						pool.remove(lastUsed);
//					}
//				}
//
//				// ---- 连接数没有达到上线，建立连接并返回
//				// 判断route是否有可用连接
//				if (pool.getAllocatedCount() < maxPerRoute) {
//					final int totalUsed = this.leased.size();
//					final int freeCapacity = Math.max(this.maxTotal - totalUsed, 0);
//					// 判断全局的全部连接都被使用中，如果存在没使用中的
//					if (freeCapacity > 0) {
//						final int totalAvailable = this.available.size();
//						if (totalAvailable > freeCapacity - 1) {
//							if (!this.available.isEmpty()) {
//								final E lastUsed = this.available.removeLast();
//								lastUsed.close();
//								final RouteSpecificPool<T, C, E> otherpool = getPool(lastUsed.getRoute());
//								otherpool.remove(lastUsed);
//							}
//						}
//						final C conn = this.connFactory.create(route);
//						// 基于route的pool添加连接
//						entry = pool.add(conn);
//						// total leased链表添加连接。
//						this.leased.add(entry);
//						return entry;
//					}
//				}
//
//				//  ----------------- 已经达到了最大连接数，尝试在timeout(从池中获取连接)之前收到通知。
//				// 如果收到通知，就进入下一轮竞争，因此是signalAll. 如果等待超时，break 后抛出timeout exception.
//				boolean success = false;
//				try {
//					// 线程池没有可用连接，将连接放入待处理集合等待，如果等待超时connectionRequestTimeout, 抛出异常。
//					// future加入全局和route的pending集合
//					pool.queue(future);
//					this.pending.add(future);
//					if (deadline != null) {
//						// 阻塞 等待 signal / signalAll
//						// void await(long) 当前线程进入等待状态直到被"通知"，"中断"或者"超时"，
//						// boolean awaitUntil(Date)  当前线程进入等待状态直到被"通知"，"中断"或者"到了某个时间"， 如果没到指定时间就被通知，返回true; 如果到了指定时间，返回false
//						success = this.condition.awaitUntil(deadline);
//					} else {
//						this.condition.await();
//						success = true;
//					}
//					if (future.isCancelled()) {
//						throw new ExecutionException(operationAborted());
//					}
//				} finally {
//					pool.unqueue(future);
//					this.pending.remove(future);
//				}
//				// 没有等到通知并且超时，timeout exception
//				if (!success && (deadline != null && deadline.getTime() <= System.currentTimeMillis())) {
//					break;
//				}
//			} // end loop
//			throw new TimeoutException("Timeout waiting for connection");
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//
//	//
//	// -------- 把连接从使用中集合删除，放入可用集合，如果有待处理任务，则唤醒获取连接线程。
//	// 当调用释放连接接口
//	// 从全局和对应route的leased中删除，由于重用，把该连接放入全局和route的available中
//	// 如果该route或者全局中还有待处理的任务future, 唤醒获取连接的线程。
//	//
//	// HttpClient的ReponseEntityProxy -》 releaseConnection
//	// http请求完成在reponse相关位置调用释放http连接
//	// reusable = true
//	//
//	@Override
//	public void release(final E entry, final boolean reusable) {
//		this.lock.lock();
//		try {
//			// -----------------------------------------------
//			// 从全局和对应route的leased链表删除，
//			// 重新加入全局和对应route的available，由于reusable = true
//			if (this.leased.remove(entry)) {
//				final RouteSpecificPool<T, C, E> pool = getPool(entry.getRoute());
//				// 从route leased链表中删除, 因为reusable=true, 连接entry加入route available
//				pool.free(entry, reusable);
//				if (reusable && !this.isShutDown) {
//					// 执行这里, 加入全局 available链表
//					this.available.addFirst(entry);
//				} else {
//					entry.close();
//				}
//
//				// do nothing
//				onRelease(entry);
//				// 从route pending中取一个并从链表中删除
//				// 如果没有，就从全局中取一个
//				// 如果有任务，就唤醒获取连接线程
//				Future<E> future = pool.nextPending();
//				if (future != null) {
//					this.pending.remove(future);
//				} else {
//					future = this.pending.poll();
//				}
//				if (future != null) {
//					// 唤醒阻塞线程
//					this.condition.signalAll();
//				}
//			}
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	private int getMax(final T route) {
//		final Integer v = this.maxPerRoute.get(route);
//		return v != null ? v.intValue() : this.defaultMaxPerRoute;
//	}
//
//	@Override
//	public void setMaxTotal(final int max) {
//		Args.positive(max, "Max value");
//		this.lock.lock();
//		try {
//			this.maxTotal = max;
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public int getMaxTotal() {
//		this.lock.lock();
//		try {
//			return this.maxTotal;
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public void setDefaultMaxPerRoute(final int max) {
//		Args.positive(max, "Max per route value");
//		this.lock.lock();
//		try {
//			this.defaultMaxPerRoute = max;
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public int getDefaultMaxPerRoute() {
//		this.lock.lock();
//		try {
//			return this.defaultMaxPerRoute;
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public void setMaxPerRoute(final T route, final int max) {
//		Args.notNull(route, "Route");
//		this.lock.lock();
//		try {
//			if (max > -1) {
//				this.maxPerRoute.put(route, Integer.valueOf(max));
//			} else {
//				this.maxPerRoute.remove(route);
//			}
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public int getMaxPerRoute(final T route) {
//		Args.notNull(route, "Route");
//		this.lock.lock();
//		try {
//			return getMax(route);
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public PoolStats getTotalStats() {
//		this.lock.lock();
//		try {
//			return new PoolStats(
//					this.leased.size(),
//					this.pending.size(),
//					this.available.size(),
//					this.maxTotal);
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	@Override
//	public PoolStats getStats(final T route) {
//		Args.notNull(route, "Route");
//		this.lock.lock();
//		try {
//			final RouteSpecificPool<T, C, E> pool = getPool(route);
//			return new PoolStats(
//					pool.getLeasedCount(),
//					pool.getPendingCount(),
//					pool.getAvailableCount(),
//					getMax(route));
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	/**
//	 * Returns snapshot of all knows routes
//	 * @return the set of routes
//	 *
//	 * @since 4.4
//	 */
//	public Set<T> getRoutes() {
//		this.lock.lock();
//		try {
//			return new HashSet<T>(routeToPool.keySet());
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	/**
//	 * Enumerates all available connections.
//	 *
//	 * @since 4.3
//	 */
//	protected void enumAvailable(final PoolEntryCallback<T, C> callback) {
//		this.lock.lock();
//		try {
//			final Iterator<E> it = this.available.iterator();
//			while (it.hasNext()) {
//				final E entry = it.next();
//				callback.process(entry);
//				if (entry.isClosed()) {
//					final RouteSpecificPool<T, C, E> pool = getPool(entry.getRoute());
//					pool.remove(entry);
//					it.remove();
//				}
//			}
//			purgePoolMap();
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	/**
//	 * Enumerates all leased connections.
//	 *
//	 * @since 4.3
//	 */
//	protected void enumLeased(final PoolEntryCallback<T, C> callback) {
//		this.lock.lock();
//		try {
//			final Iterator<E> it = this.leased.iterator();
//			while (it.hasNext()) {
//				final E entry = it.next();
//				callback.process(entry);
//			}
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//	private void purgePoolMap() {
//		final Iterator<Map.Entry<T, RouteSpecificPool<T, C, E>>> it = this.routeToPool.entrySet().iterator();
//		while (it.hasNext()) {
//			final Map.Entry<T, RouteSpecificPool<T, C, E>> entry = it.next();
//			final RouteSpecificPool<T, C, E> pool = entry.getValue();
//			if (pool.getPendingCount() + pool.getAllocatedCount() == 0) {
//				it.remove();
//			}
//		}
//	}
//
//	/**
//	 * Closes connections that have been idle longer than the given period
//	 * of time and evicts them from the pool.
//	 *
//	 * @param idletime maximum idle time.
//	 * @param timeUnit time unit.
//	 */
//	public void closeIdle(final long idletime, final TimeUnit timeUnit) {
//		Args.notNull(timeUnit, "Time unit");
//		long time = timeUnit.toMillis(idletime);
//		if (time < 0) {
//			time = 0;
//		}
//		final long deadline = System.currentTimeMillis() - time;
//		enumAvailable(new PoolEntryCallback<T, C>() {
//
//			@Override
//			public void process(final PoolEntry<T, C> entry) {
//				if (entry.getUpdated() <= deadline) {
//					entry.close();
//				}
//			}
//
//		});
//	}
//
//	/**
//	 * Closes expired connections and evicts them from the pool.
//	 */
//	public void closeExpired() {
//		final long now = System.currentTimeMillis();
//		enumAvailable(new PoolEntryCallback<T, C>() {
//
//			@Override
//			public void process(final PoolEntry<T, C> entry) {
//				if (entry.isExpired(now)) {
//					entry.close();
//				}
//			}
//
//		});
//	}
//
//	/**
//	 * @return the number of milliseconds
//	 * @since 4.4
//	 */
//	public int getValidateAfterInactivity() {
//		return this.validateAfterInactivity;
//	}
//
//	/**
//	 * @param ms the number of milliseconds
//	 * @since 4.4
//	 */
//	public void setValidateAfterInactivity(final int ms) {
//		this.validateAfterInactivity = ms;
//	}
//
//	@Override
//	public String toString() {
//		this.lock.lock();
//		try {
//			final StringBuilder buffer = new StringBuilder();
//			buffer.append("[leased: ");
//			buffer.append(this.leased);
//			buffer.append("][available: ");
//			buffer.append(this.available);
//			buffer.append("][pending: ");
//			buffer.append(this.pending);
//			buffer.append("]");
//			return buffer.toString();
//		} finally {
//			this.lock.unlock();
//		}
//	}
//
//}
//
