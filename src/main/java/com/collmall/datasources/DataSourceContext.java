package com.collmall.datasources;

/**
 * @author xulihui
 * @date 2019-01-25
 */
public class DataSourceContext {

    private static final ThreadLocal<DataSourceContext> _localContext = new ThreadLocal<>();

    private String _identity;
    private boolean _lock = false;

    public String getIdentity() {
        return _identity;
    }

    public void setIdentity(String identity) {
        if (this._lock) {
            if (_identity != null && !identity.equalsIgnoreCase(_identity)) {
                throw new RuntimeException("在事务开始后又改变了路由异常!");
            }
        }
        _identity = identity;
    }

    private DataSourceContext() {
        _localContext.set(this);
    }

    public void lock() {
        this._lock = true;
    }

    public void unlock() {
        this._lock = false;
    }

    public static DataSourceContext getContext() {
        DataSourceContext context = _localContext.get();
        if (context == null)
            return new DataSourceContext();
        else
            return context;
    }
}
