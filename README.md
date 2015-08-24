# JdeRobot-VehicleCounter-GSOC2015
VehicleCounter components developed by Satyaki Chakraborty in GSoC 2015 program

# Contents
<b>models:</b> contains gazebo model (car) used in the c++ component
<br><br>
<b>plugins:</b> contains carplugin.cc (plugin) for car model.
<br>
NOTE: carplugin has been developed to be run with ArDrone_simple-gsoc.world/ArDrone_texture-gsoc.world
<br>
CMakeLists.txt assumes that the plugin is under gazeboserver/plugins directory as in <a href="https://github.com/shady-cs15/JdeRobot/tree/heatmap/src/stable/components/gazeboserver/plugins/">here</a>
<br><br>
<b>worlds:</b> contains gazebo worlds gazebo worlds
<br><br>
<b>src:</b> contains the source code for the following components --
<p><b>1. vehicleCounter:</b> c++ component which uses the visual feed from the ArDrone to generate a traffic heatmap of certain checkpoints. Vehicle detection was by obtaining a foreground mask of moving vehicles after adaptive background subtraction using a GMM method and tracking was done using cvBlobs.
<br><p><b>2. wearClient:</b> android component consisting of two modules one for the handheld device (mobile) and the other for the wearable (wear). This component is used to show the heatmap information from the C++ component in the wearable itself.
<br><p><b>3. wearTeleoperator:</b> android component also consisting of mobile and wear modules, used for teleoperating the ArDrone from the wearable device.

#Installation and Running
Here it is assumed that the vehicleCounter component is in the stable components directory as in <a href="https://github.com/shady-cs15/JdeRobot/tree/heatmap/src/stable/components/">here</a>
<br><br>
<b>Installing</b><br>
1. Navigate to the root directory of JdeRobot. <br>
2. $ cmake -Dbuild-default=OFF -Dbuild_vehicleCounter=ON . <br>
3. $ make -j4
<br><br>
<b>Running</b></br>
1. navigate to the directory of the c++ component. <br>
2. $ ./vehicleCounter --Ice.Config=vehicleCounter.cfg </br>
<br><br>
For more information on installation and running please visit <a href="http://jderobot.org/Chakraborty-colab#Documentation">here</a>