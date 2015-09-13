CMAKE_MINIMUM_REQUIRED(VERSION 2.8)

#generate .cpp .h from .ice files
UNSET(ICE_FILES CACHE)
FILE(GLOB ICE_FILES "./interfaces/slice/jderobot/*.ice")
FILE(MAKE_DIRECTORY ${CMAKE_CURRENT_SOURCE_DIR}/interfaces/cpp/jderobot)

SET(SLICE_OUTPUT ${CMAKE_CURRENT_SOURCE_DIR}/interfaces/cpp/jderobot)

FOREACH(currentSourceFile ${ICE_FILES})
	string(REGEX REPLACE ".*/(.*)" "\\1" new_source ${currentSourceFile})
	string(REGEX REPLACE ".*/(.*).ice" "\\1.cpp" new_source1 ${currentSourceFile})
	string(REGEX REPLACE ".*/(.*).ice" "\\1.h" new_source2 ${currentSourceFile})
	LIST(APPEND SOURCE_FILES ${SLICE_OUTPUT}/${new_source1})
	LIST(APPEND SOURCE_FILES ${SLICE_OUTPUT}/${new_source2})
	MESSAGE("-- ${new_source}: ${new_source1}, ${new_source2}")

	execute_process(COMMAND slice2cpp ${currentSourceFile} -I${CMAKE_CURRENT_SOURCE_DIR}/interfaces/slice/jderobot --output-dir ${SLICE_OUTPUT})
ENDFOREACH(currentSourceFile)

ADD_LIBRARY(JdeRobotCppInterfaces SHARED ${SOURCE_FILES} ${ICE_FILES})

#vehicleCounter c++ 
SET(INTERFACES_CPP_DIR ${CMAKE_CURRENT_SOURCE_DIR}/interfaces/cpp)
SET(SOURCE_CPP ${CMAKE_CURRENT_SOURCE_DIR}/src/vehicleCounter/vehicleCounter.cpp)
SET(LIBS_DIR /usr/local/lib/jderobot)

find_package(OpenCV REQUIRED)

FIND_PATH( Ice_INCLUDE_DIR NAMES Ice/Ice.h  PATHS ENV C++LIB ENV)
IF( Ice_INCLUDE_DIR )
    FIND_LIBRARY( Ice_LIBRARY1 NAMES Ice PATHS ENV C++LIB ENV PATH PATH_SUFFIXES lib lib64 )
    FIND_LIBRARY( Ice_LIBRARY2 NAMES IceUtil PATHS ENV C++LIB ENV PATH PATH_SUFFIXES lib lib64)
    SET (Ice_LIBRARIES ${Ice_LIBRARY1} ${Ice_LIBRARY2})
    IF( Ice_LIBRARIES )
                MESSAGE ("-- Ice found at ${Ice_LIBRARIES}")
		TARGET_LINK_LIBRARIES(JdeRobotCppInterfaces ${Ice_LIBRARY2})
    ENDIF( Ice_LIBRARIES )
ENDIF(Ice_INCLUDE_DIR)
IF(NOT Ice_LIBRARIES)
                MESSAGE ("*** Ice not found")
ENDIF()

find_package(cvBlob REQUIRED)

include_directories(
	${LIBS_DIR}
	${OpenCV_INCLUDE_DIR}
	${Ice_INCLUDE_DIR}
	${INTERFACES_CPP_DIR}
	${INTERFACES_CPP_DIR}/jderobot)

link_directories(${JDE_LIBS} ${CMAKE_CURRENT_SOURCE_DIR})

ADD_SUBDIRECTORY(${CMAKE_CURRENT_SOURCE_DIR}/src/vehicleCounter)
ADD_SUBDIRECTORY(${CMAKE_CURRENT_SOURCE_DIR}/plugins)

