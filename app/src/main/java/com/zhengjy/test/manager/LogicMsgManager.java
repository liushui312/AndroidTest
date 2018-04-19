package com.zhengjy.test.manager;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.LinkedHashSet;

/**
 * 消息管理类，用于UI管理注册和解注册，第一次注册的时候，会注册到逻辑层
 * Created by WangMuHuo(yl1670) on 2018/1/17.
 */

public class LogicMsgManager {

    private static final String TAG = "LogicMsgManager";
    private static LogicMsgManager sInstance = new LogicMsgManager();

    private final SparseArray<LinkedHashSet<Handler>> mMapObservers = new SparseArray<>();
    private final SparseIntArray mAllId = new SparseIntArray();

   /* private IMessageHandler mMessageHandler = new IMessageHandler.Stub() {
        @Override
        public void onMessage(Message msg) throws RemoteException {
            Log.d(TAG, "onMessage what:" + msg.what + " arg1:" + msg.arg1 + " arg2:" + msg.arg2 + ";currentThread:" + Thread.currentThread());
            processMessage(msg);
        }
    };
*/
    private LogicMsgManager() {

    }

    public static LogicMsgManager getInstance() {
        return sInstance;
    }

    public void register(int formIdToId, Handler handler) {
        if (handler == null) return;
        synchronized (mMapObservers) {
            LinkedHashSet<Handler> observers = mMapObservers.get(formIdToId);
            if (observers == null) {
                observers = new LinkedHashSet<>();
                mMapObservers.put(formIdToId, observers);
            }
            observers.add(handler);
        }
        synchronized (mAllId) {
            if (mAllId.get(formIdToId) == 0) {
                mAllId.put(formIdToId, 1);
                //LogicManager.getInstance().RegisterMessage(formIdToId, formIdToId, mMessageHandler);
            }
        }
    }

    public void unRegister(int formIdToId, Handler handler) {
        if (handler == null) return;
        synchronized (mMapObservers) {
            LinkedHashSet<Handler> observers = mMapObservers.get(formIdToId);
            if (observers != null) {
                observers.remove(handler);
            }
        }
    }

    private void processMessage(Message msg) {
        synchronized (mMapObservers) {
            LinkedHashSet<Handler> observers = mMapObservers.get(msg.what);
            if (observers != null) {
//                Log.d(TAG, "processMessage" + Arrays.toString(observers.toArray()));
                for (Handler handler : observers) {
                    // 发送之前进行浅拷贝，以防止 MessageQueue 抛出 new IllegalStateException(msg + " This message is already in use.");
                    Message message = Message.obtain(handler, msg.what, msg.arg1, msg.arg2, msg.obj);
                    message.setData(msg.getData());
                    message.sendToTarget();
                }
            }
        }
    }

}
