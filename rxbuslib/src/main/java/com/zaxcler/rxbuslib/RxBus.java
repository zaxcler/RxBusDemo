package com.zaxcler.rxbuslib;



import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by zaxcler on 2017/3/3.
 */

public class RxBus {

    private static volatile RxBus mInstance;

    private final Subject bus;


    public RxBus()
    {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例模式RxBus
     *
     * @return
     */
    public static RxBus getInstance()
    {

        if (mInstance == null)
        {
            synchronized (RxBus.class)
            {
                if (mInstance == null)
                {
                    mInstance = new RxBus();
                }
            }
        }

        return mInstance;
    }


    /**
     * 发送消息
     *
     * @param object
     */
    public void post(Object object)
    {

        bus.onNext(object);

    }

    /**
     * 接收消息
     *
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(Class<T> eventType)
    {
        return bus.ofType(eventType);
    }


    /**
     * 提供了一个新的事件,根据code进行分发
     * @param code 事件code
     * @param o
     */
    public void post(int code, Object o){
        bus.onNext(new RxMessage(code,o));

    }


    /**
     *
     * 根据传递的code和 eventType 类型返回特定类型(eventType)的 被观察者
     * @param code 事件code
     * @param eventType 事件类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObserverable(final int code, final Class<T> eventType) {
        return bus.filter(new Func1<RxMessage,Boolean>() {
                    @Override
                    public Boolean call(RxMessage o) {
                        //过滤code和eventType都相同的事件
                        return o.getCode() == code && eventType.isInstance(o.getObject());
                    }
                }).map(new Func1<RxMessage,Object>() {
                    @Override
                    public Object call(RxMessage o) {
                        return o.getObject();
                    }
                }).cast(eventType);
    }

}