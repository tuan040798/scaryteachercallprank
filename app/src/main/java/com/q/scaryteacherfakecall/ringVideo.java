package com.q.scaryteacherfakecall;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ringVideo extends AppCompatActivity   {
    Camera camera;
    CameraPreview cameraPreview;
    FrameLayout videoView;
    Boolean isCameraFront = false;
    private ImageView okVideo,cancleVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_video);

        okVideo = findViewById(R.id.okVideo);
        cancleVideo = findViewById(R.id.cancelVideo2);
        okVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), video.class);
                startActivity(myIntent);
                finish();
            }
        });

        cancleVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        videoView = findViewById(R.id.videoView);
        configCamera();
        chooseCamera();

    }
    public void configCamera() {
        camera = Camera.open();
        camera.setDisplayOrientation(90);
        cameraPreview = new CameraPreview(this, camera);
        camera.setAutoFocusMoveCallback(new Camera.AutoFocusMoveCallback() {
            @Override
            public void onAutoFocusMoving(boolean start, Camera camera) {

            }
        });
        videoView.addView(cameraPreview);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("asas", "onStop: ");
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    private int findBackFacingCamera() {
        int cameraId = -1;
        //Search for the back facing camera
        //get the number of cameras
        int numberOfCameras = Camera.getNumberOfCameras();
        //for every camera check
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i;
                isCameraFront = false;
                return i;
//                break;
            }
        }
        return cameraId;
    }
    private int findFrontFacingCamera() {
        int cameraId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i;
                isCameraFront = true;
                return i;
            }
        }
        return cameraId;
    }
    public void chooseCamera() {
        // stop and release camera
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
        camera = null;
        if (isCameraFront) {
            int cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                camera = Camera.open(cameraId);
                camera.setDisplayOrientation(90);
                this.cameraPreview.refreshCamera(camera);
            }
        } else {
            int cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                camera = Camera.open(cameraId);
                camera.setDisplayOrientation(90);
                this.cameraPreview.refreshCamera(camera);
            }
        }
    }
}
