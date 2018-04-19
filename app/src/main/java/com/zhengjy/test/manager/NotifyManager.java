package com.zhengjy.test.manager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.yealink.android.enumdefine.CascadeMessageDefine;
import com.yealink.android.utils.YLog;
import com.yealink.cascade.listener.CascadeMasterListener;
import com.yealink.cascade.listener.CascadeSlaveListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author zhengjy
 * @date 2018/3/27
 */

public class NotifyManager {
	public static final String TAG = NotifyManager.class.getSimpleName();

	private static NotifyManager sInstance;

	public synchronized static NotifyManager getInstance() {
		if (sInstance == null) {
			sInstance = new NotifyManager();
		}
		return sInstance;
	}

	private NotifyManager() {
		init();
	}

	private ReentrantLock mLsnrLock = new ReentrantLock();
/*	private List<CascadeMasterListener> mCascadeMasterLsnrs  = new ArrayList<>();
	private List<CascadeSlaveListener> mCascadeSlaveLsnrs = new ArrayList<>();*/

	private Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			Log.d(TAG, "handleMessage name: " + getMsgName(msg.what) + ", msg:" + msg);
			switch (msg.what) {
				/*case CascadeMessageDefine.CASCADE_CLIENT_PREJOIN:
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onAuthentication();
					}
					break;
				case CascadeMessageDefine.CASCADE_CLIENT_JOINCASCADERESULT:
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onJoinFinished(msg.arg1, msg.arg2==1);
					}
					break;
				case CascadeMessageDefine.CASCADE_SERVICE_NEWSLAVE_ONLINE:
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onNewSlaveJoined();
					}
					break;
				case CascadeMessageDefine.CASCADE_SERVICE_SLAVE_OFFLINE:
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onSlaveOffLine();
					}
					break;
				case CascadeMessageDefine.CASCADE_CLIENT_EXIT_GROUP:
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onSlaveExited(msg.arg1);
					}
					break;
				case CascadeMessageDefine.CASCADE_CLIENT_SERVICEFIND:
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onServiceFound();
					}
					break;
				case CascadeMessageDefine.CASCADE_SERVICE_REGISTERED:
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onServicePublished(true);
					}
					break;
				case CascadeMessageDefine.CASCADE_SERVICE_REGISTER_FAILED:
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onServicePublished(false);
					}
					break;
				case CascadeMessageDefine.CASCADE_SERVICE_UNREGISTERED:
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onStopServiceFinished(true);
					}
					break;
				case CascadeMessageDefine.CASCADE_SERVICE_UNREGISTER_FAIL:
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onStopServiceFinished(false);
					}
					break;
				case CascadeMessageDefine.CASCADE_CASCADEMEM_CHANGE:
					String mac = "";
					if (msg.getData() != null) {
						mac = msg.getData().getString("mac");
					}
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onMemberChanged(msg.arg1, msg.arg2, mac);
					}
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onMemberChanged(msg.arg1, msg.arg2, mac);
					}
					break;
				case CascadeMessageDefine.CASCADE_DEVICE_EXIT_FIND:
					String ip = "";
					if (msg.getData() != null) {
						ip = msg.getData().getString("ip");
					}
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onDeviceExitFind(ip);
					}
					for (CascadeMasterListener cascadeLsnr : mCascadeMasterLsnrs) {
						cascadeLsnr.onDeviceExitFind(ip);
					}
					break;
				case CascadeMessageDefine.CASCADE_CLIENT_DISCONNECTTOSERVER:
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onServiceDisconnect();
					}
					break;
				case CascadeMessageDefine.CASCADE_CLIENT_CONNECTEDTOSERVER:
					for (CascadeSlaveListener cascadeLsnr : mCascadeSlaveLsnrs) {
						cascadeLsnr.onServiceConnect();
					}
					break;
				case CascadeMessageDefine.CASCADE_CASCADE_DESTORY:
					for (CascadeMasterListener cascadeMasterLsnr : mCascadeMasterLsnrs) {
						cascadeMasterLsnr.onCascadeDestroy();
					}
					break;
				case CascadeMessageDefine.CASCADE_CLIENT_SERVICEOFFLINE:
					for (CascadeSlaveListener cascadeSlaveLsnr : mCascadeSlaveLsnrs) {
						cascadeSlaveLsnr.onPublishServiceOffLine();
					}
					break;*/
				default:
					break;
			}
			return false;
		}
	});

	private void init() {
		Log.d(TAG, "init");
		/*LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_PREJOIN, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_JOINCASCADERESULT, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_NEWSLAVE_ONLINE, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_SLAVE_OFFLINE, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_EXIT_GROUP, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_SERVICEFIND, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_REGISTERED, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_REGISTER_FAILED, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_UNREGISTERED, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_UNREGISTER_FAIL, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CASCADEMEM_CHANGE, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_DEVICE_EXIT_FIND, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_CONNECTEDTOSERVER, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_DISCONNECTTOSERVER, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CASCADE_DESTORY, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_CLIENT_SERVICEOFFLINE, mHandler);

		//未处理的消息
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_NEWCLIENTCONECTED, mHandler);
		LogicMsgManager.getInstance().register(CascadeMessageDefine.CASCADE_SERVICE_CLIENTDISCONNECT, mHandler);*/
	}

	/**
	 * 注册聊天消息变化的监听器
	 * @param cascadeListener
	 *//*
	public void registerCascadeSlaveLsnr(CascadeSlaveListener cascadeListener){
		mLsnrLock.lock();
		try{
			mCascadeSlaveLsnrs.add(cascadeListener);
			YLog.i(TAG,"registerCascadeSlaveLsnr : " + mCascadeSlaveLsnrs.size());
		}finally {
			mLsnrLock.unlock();
		}
	}

	*//**
	 * 取消注册聊天消息变化的监听器
	 * @param cascadeListener
	 *//*
	public void unRegisterCascadeSlaveLsnr(CascadeSlaveListener cascadeListener){
		mLsnrLock.lock();
		try{
			mCascadeSlaveLsnrs.remove(cascadeListener);
			YLog.i(TAG,"unRegisterCascadeSlaveLsnr : " + mCascadeSlaveLsnrs.size());
		}finally {
			mLsnrLock.unlock();
		}
	}

	*//**
	 * 注册聊天消息变化的监听器
	 * @param cascadeListener
	 *//*
	public void registerCascadeMasterLsnr(CascadeMasterListener cascadeListener){
		mLsnrLock.lock();
		try{
			mCascadeMasterLsnrs.add(cascadeListener);
			YLog.i(TAG,"registerCascadeMasterLsnr : " + mCascadeMasterLsnrs.size());
		}finally {
			mLsnrLock.unlock();
		}
	}

	*//**
	 * 取消注册聊天消息变化的监听器
	 * @param cascadeListener
	 *//*
	public void unRegisterCascadeMasterLsnr(CascadeMasterListener cascadeListener){
		mLsnrLock.lock();
		try{
			mCascadeMasterLsnrs.remove(cascadeListener);
			YLog.i(TAG,"unRegisterCascadeMasterLsnr : " + mCascadeMasterLsnrs.size());
		}finally {
			mLsnrLock.unlock();
		}
	}*/

	private String getMsgName(int msgId) {
		String name = "";
		switch (msgId) {
			/*case CascadeMessageDefine.CASCADE_CASCADEMEM_CHANGE:
				name = "CASCADE_CASCADEMEM_CHANGE";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_CONNECTEDTOSERVER:
				name = "CASCADE_CLIENT_CONNECTEDTOSERVER";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_DISCONNECTTOSERVER:
				name = "CASCADE_CLIENT_DISCONNECTTOSERVER";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_EXIT_GROUP:
				name = "CASCADE_CLIENT_EXIT_GROUP";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_JOINCASCADERESULT:
				name = "CASCADE_CLIENT_JOINCASCADERESULT";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_PREJOIN:
				name = "CASCADE_CLIENT_PREJOIN";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_SERVICEFIND:
				name = "CASCADE_CLIENT_SERVICEFIND";
				break;
			case CascadeMessageDefine.CASCADE_CLIENT_SERVICEOFFLINE:
				name = "CASCADE_CLIENT_SERVICEOFFLINE";
				break;
			case CascadeMessageDefine.CASCADE_DEVICE_EXIT_FIND:
				name = "CASCADE_DEVICE_EXIT_FIND";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_CLIENTDISCONNECT:
				name = "CASCADE_SERVICE_CLIENTDISCONNECT";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_UNREGISTER_FAIL:
				name = "CASCADE_SERVICE_UNREGISTER_FAIL";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_UNREGISTERED:
				name = "CASCADE_SERVICE_UNREGISTER_FAIL";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_REGISTERED:
				name = "CASCADE_SERVICE_REGISTERED";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_NEWCLIENTCONECTED:
				name = "CASCADE_SERVICE_NEWCLIENTCONECTED";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_REGISTER_FAILED:
				name = "CASCADE_SERVICE_REGISTER_FAILED";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_NEWSLAVE_ONLINE:
				name = "CASCADE_SERVICE_NEWSLAVE_ONLINE";
				break;
			case CascadeMessageDefine.CASCADE_SERVICE_SLAVE_OFFLINE:
				name = "CASCADE_SERVICE_SLAVE_OFFLINE";
				break;
			case CascadeMessageDefine.CASCADE_CASCADE_DESTORY:
				name = "CASCADE_CASCADE_DESTORY";
				break;*/
			default:
				break;
		}

		return name;
	}
}
