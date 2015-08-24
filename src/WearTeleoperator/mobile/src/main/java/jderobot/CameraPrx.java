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
// Generated from file `camera.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package jderobot;

/**
 * Camera interface
 **/
public interface CameraPrx extends ImageProviderPrx
{
    public CameraDescription getCameraDescription();

    public CameraDescription getCameraDescription(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getCameraDescription();

    public Ice.AsyncResult begin_getCameraDescription(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_getCameraDescription(Ice.Callback __cb);

    public Ice.AsyncResult begin_getCameraDescription(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_getCameraDescription(Callback_Camera_getCameraDescription __cb);

    public Ice.AsyncResult begin_getCameraDescription(java.util.Map<String, String> __ctx, Callback_Camera_getCameraDescription __cb);

    public CameraDescription end_getCameraDescription(Ice.AsyncResult __result);

    public int setCameraDescription(CameraDescription description);

    public int setCameraDescription(CameraDescription description, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_setCameraDescription(CameraDescription description);

    public Ice.AsyncResult begin_setCameraDescription(CameraDescription description, java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_setCameraDescription(CameraDescription description, Ice.Callback __cb);

    public Ice.AsyncResult begin_setCameraDescription(CameraDescription description, java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_setCameraDescription(CameraDescription description, Callback_Camera_setCameraDescription __cb);

    public Ice.AsyncResult begin_setCameraDescription(CameraDescription description, java.util.Map<String, String> __ctx, Callback_Camera_setCameraDescription __cb);

    public int end_setCameraDescription(Ice.AsyncResult __result);

    public String startCameraStreaming();

    public String startCameraStreaming(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_startCameraStreaming();

    public Ice.AsyncResult begin_startCameraStreaming(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_startCameraStreaming(Ice.Callback __cb);

    public Ice.AsyncResult begin_startCameraStreaming(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_startCameraStreaming(Callback_Camera_startCameraStreaming __cb);

    public Ice.AsyncResult begin_startCameraStreaming(java.util.Map<String, String> __ctx, Callback_Camera_startCameraStreaming __cb);

    public String end_startCameraStreaming(Ice.AsyncResult __result);

    public void stopCameraStreaming();

    public void stopCameraStreaming(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_stopCameraStreaming();

    public Ice.AsyncResult begin_stopCameraStreaming(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_stopCameraStreaming(Ice.Callback __cb);

    public Ice.AsyncResult begin_stopCameraStreaming(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_stopCameraStreaming(Callback_Camera_stopCameraStreaming __cb);

    public Ice.AsyncResult begin_stopCameraStreaming(java.util.Map<String, String> __ctx, Callback_Camera_stopCameraStreaming __cb);

    public void end_stopCameraStreaming(Ice.AsyncResult __result);

    public void reset();

    public void reset(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_reset();

    public Ice.AsyncResult begin_reset(java.util.Map<String, String> __ctx);

    public Ice.AsyncResult begin_reset(Ice.Callback __cb);

    public Ice.AsyncResult begin_reset(java.util.Map<String, String> __ctx, Ice.Callback __cb);

    public Ice.AsyncResult begin_reset(Callback_Camera_reset __cb);

    public Ice.AsyncResult begin_reset(java.util.Map<String, String> __ctx, Callback_Camera_reset __cb);

    public void end_reset(Ice.AsyncResult __result);
}