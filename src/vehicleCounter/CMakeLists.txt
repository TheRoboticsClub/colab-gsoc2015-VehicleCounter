
add_executable(vehicleCounter 
	${SOURCE_CPP}
	${INTERFACES_CPP_DIR})
	
target_link_libraries(vehicleCounter
	${CMAKE_THREAD_LIBS_INIT}
	${Ice_LIBRARIES}
	${ZeroCIce_LIBRARIES}
	${OpenCV_LIBRARIES}
	${OpenCVGUI_LIBRARIES}
	${cvBlob_LIBS}
	libJdeRobotCppInterfaces.so
	${LIBS_DIR}/libjderobotutil.so
	-lpthread)
