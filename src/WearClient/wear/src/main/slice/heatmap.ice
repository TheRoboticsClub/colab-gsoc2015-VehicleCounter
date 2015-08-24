/*
 *  Copyright (C) 1997-2015 JDERobot Developers Team
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Library General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 *
 *  Author: Satyaki Chakraborty (satyaki [dot] cs15 [at] gmail [dot] com)
 *
 *  Note: This source code has been developed in GSoC'2015
 *
 */

#ifndef HEATMAP_ICE
#define HEATMAP_ICE

#include "pose3d.ice"

module jderobot {
	class checkpoint {
		int x;
		int y;
	};

	sequence<checkpoint> checkpointList;
	
	class HeatmapInfo {
		int width;
		int height;
		checkpointList cpList;
	};

	class HeatmapData {
		bool state;
		int curcp;
		float curvel;
		int curfreq;
	};

	interface Heatmap {
		HeatmapData getHeatmapData();
		HeatmapInfo getHeatmapInfo();
		Pose3DData getDronePose();
	};
};

#endif
