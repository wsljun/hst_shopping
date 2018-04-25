package com.cj.reocrd.net;


/**
 * @类名称：NetChangeObserver * @类描述： 是检测网络改变的观察者

 */
public interface NetChangeObserver {
    /**
     * 网络连接连接时调用
     */
     void onConnect(NetWorkUtil.NetType type) ;

    /**
     * 当前没有网络连接
     */
     void onDisConnect() ;

}
