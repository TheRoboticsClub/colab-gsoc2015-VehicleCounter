
find_package(Boost REQUIRED COMPONENTS system)
include_directories(${Boost_INCLUDE_DIRS})
link_directories(${Boost_LIBRARY_DIRS})

include (FindPkgConfig)
if (PKG_CONFIG_FOUND)
  pkg_check_modules(GAZEBO gazebo)
endif()

include_directories(${GAZEBO_INCLUDE_DIRS}
		    ${Boost_INCLUDE_DIR})

link_directories(${GAZEBO_LIBRARY_DIRS}
		 ${Boost_LIBRARIES})

add_library(carplugin SHARED carplugin.cc)
target_link_libraries(carplugin 
		${GAZEBO_LIBRARIES} 
		${Boost_LIBRARIES})
