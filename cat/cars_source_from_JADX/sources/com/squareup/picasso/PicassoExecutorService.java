package com.squareup.picasso;

import android.net.NetworkInfo;
import com.squareup.picasso.Picasso.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    private static final class PicassoFutureTask extends FutureTask<BitmapHunter> implements Comparable<PicassoFutureTask> {
        private final BitmapHunter hunter;

        PicassoFutureTask(BitmapHunter hunter2) {
            super(hunter2, null);
            this.hunter = hunter2;
        }

        public int compareTo(PicassoFutureTask other) {
            int i;
            int i2;
            Priority p1 = this.hunter.getPriority();
            Priority p2 = other.hunter.getPriority();
            if (p1 == p2) {
                i2 = this.hunter.sequence;
                i = other.hunter.sequence;
            } else {
                i2 = p2.ordinal();
                i = p1.ordinal();
            }
            return i2 - i;
        }
    }

    PicassoExecutorService() {
        super(3, 3, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    /* access modifiers changed from: 0000 */
    public void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(3);
            return;
        }
        int type = info.getType();
        if (type == 0) {
            int subtype = info.getSubtype();
            switch (subtype) {
                case 1:
                case 2:
                    setThreadCount(1);
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                    setThreadCount(2);
                    break;
                default:
                    switch (subtype) {
                        case 12:
                            break;
                        case 13:
                        case 14:
                        case 15:
                            setThreadCount(3);
                            break;
                        default:
                            setThreadCount(3);
                            break;
                    }
                    setThreadCount(2);
                    break;
            }
        } else if (type == 1 || type == 6 || type == 9) {
            setThreadCount(4);
        } else {
            setThreadCount(3);
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }

    public Future<?> submit(Runnable task) {
        PicassoFutureTask ftask = new PicassoFutureTask((BitmapHunter) task);
        execute(ftask);
        return ftask;
    }
}
