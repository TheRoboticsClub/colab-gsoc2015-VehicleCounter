# JdeRobot-VehicleCounter-GSOC2015
VehicleCounter components developed by Satyaki Chakraborty in GSoC 2015 program

# Contents
<b>models:</b> contains gazebo model (car) used in the c++ component
<br><br>
<b>plugins:</b> contains carplugin.cc (plugin) for car model.
<br><br>
<b>worlds:</b> contains gazebo worlds gazebo worlds
<br><br>
<b>interfaces:</b> contains heatmap.ice Interface and others used by vehicleCounter and android wear components<br>
<br><br>
<b>src:</b> contains the source code for the following components --
<p><b>1. vehicleCounter:</b> c++ component which uses the visual feed from the ArDrone to generate a traffic heatmap of certain checkpoints. Vehicle detection was by obtaining a foreground mask of moving vehicles after adaptive background subtraction using a GMM method and tracking was done using cvBlobs.
<br><p><b>2. wearClient:</b> android component consisting of two modules one for the handheld device (mobile) and the other for the wearable (wear). This component is used to show the heatmap information from the C++ component in the wearable itself.
<br><p><b>3. wearTeleoperator:</b> android component also consisting of mobile and wear modules, used for teleoperating the ArDrone from the wearable device.

#Installation and Running
<br><br>
<b>Installing</b><br>
1. Navigate to the root directory of the project. <br>
2. $ cmake. <br>
3. $ make -j4 <br>
4. $ export GAZEBO_PLUGIN_PATH=${GAZEBO_PLUGIN_PATH}:<project_dir>/plugins <br>
5. $ export GAZEBO_MODEL_PATH=${GAZEBO_MODEL_PATH}:<project_dir>/models <br>
<br><br>
<b>Running</b></br>
1. navigate to 'worlds' folder. <br>
2. gzserver ArDrone_simple-gsoc.world. <br>
3. navigate to the directory of the c++ component. <br>
4. $ ./vehicleCounter --Ice.Config=vehicleCounter.cfg </br>
<br><br>
Android components are to be seperately imported into Android Studio and therefrom installed in the devices.
For more information please visit the actual blog<a href="http://jderobot.org/Chakraborty-colab#Documentation">here</a>

#Demo Video
<a
 href = "https://www.youtube.com/watch?v=VCij_LN1CRU">
 Demo video
</a> can be found here.

#Screenshots
<img src="http://jderobot.org/store/chakraborty/uploads/images/scr1.png"/>
<img src="http://jderobot.org/store/chakraborty/uploads/images/scr2.png"/>
