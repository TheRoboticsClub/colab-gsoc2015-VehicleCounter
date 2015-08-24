// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `heatmap.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package jderobot;

public final class checkpointPrxHelper extends Ice.ObjectPrxHelperBase implements checkpointPrx
{
    public static checkpointPrx checkedCast(Ice.ObjectPrx __obj)
    {
        checkpointPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof checkpointPrx)
            {
                __d = (checkpointPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId()))
                {
                    checkpointPrxHelper __h = new checkpointPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static checkpointPrx checkedCast(Ice.ObjectPrx __obj, java.util.Map<String, String> __ctx)
    {
        checkpointPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof checkpointPrx)
            {
                __d = (checkpointPrx)__obj;
            }
            else
            {
                if(__obj.ice_isA(ice_staticId(), __ctx))
                {
                    checkpointPrxHelper __h = new checkpointPrxHelper();
                    __h.__copyFrom(__obj);
                    __d = __h;
                }
            }
        }
        return __d;
    }

    public static checkpointPrx checkedCast(Ice.ObjectPrx __obj, String __facet)
    {
        checkpointPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId()))
                {
                    checkpointPrxHelper __h = new checkpointPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static checkpointPrx checkedCast(Ice.ObjectPrx __obj, String __facet, java.util.Map<String, String> __ctx)
    {
        checkpointPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            try
            {
                if(__bb.ice_isA(ice_staticId(), __ctx))
                {
                    checkpointPrxHelper __h = new checkpointPrxHelper();
                    __h.__copyFrom(__bb);
                    __d = __h;
                }
            }
            catch(Ice.FacetNotExistException ex)
            {
            }
        }
        return __d;
    }

    public static checkpointPrx uncheckedCast(Ice.ObjectPrx __obj)
    {
        checkpointPrx __d = null;
        if(__obj != null)
        {
            if(__obj instanceof checkpointPrx)
            {
                __d = (checkpointPrx)__obj;
            }
            else
            {
                checkpointPrxHelper __h = new checkpointPrxHelper();
                __h.__copyFrom(__obj);
                __d = __h;
            }
        }
        return __d;
    }

    public static checkpointPrx uncheckedCast(Ice.ObjectPrx __obj, String __facet)
    {
        checkpointPrx __d = null;
        if(__obj != null)
        {
            Ice.ObjectPrx __bb = __obj.ice_facet(__facet);
            checkpointPrxHelper __h = new checkpointPrxHelper();
            __h.__copyFrom(__bb);
            __d = __h;
        }
        return __d;
    }

    public static final String[] __ids =
    {
        "::Ice::Object",
        "::jderobot::checkpoint"
    };

    public static String ice_staticId()
    {
        return __ids[1];
    }

    protected Ice._ObjectDelM __createDelegateM()
    {
        return new _checkpointDelM();
    }

    protected Ice._ObjectDelD __createDelegateD()
    {
        return new _checkpointDelD();
    }

    public static void __write(IceInternal.BasicStream __os, checkpointPrx v)
    {
        __os.writeProxy(v);
    }

    public static checkpointPrx __read(IceInternal.BasicStream __is)
    {
        Ice.ObjectPrx proxy = __is.readProxy();
        if(proxy != null)
        {
            checkpointPrxHelper result = new checkpointPrxHelper();
            result.__copyFrom(proxy);
            return result;
        }
        return null;
    }

    public static final long serialVersionUID = 0L;
}
