package cn.itcast.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.domain.City;
import cn.itcast.domain.Province;

public class Utils {
	private static Map<String, Province> provinceMap = new HashMap<String, Province>();
	static {
		Province BeiJingProvince = new Province(1, "BeiJing");
		Province ShangHaiProvince = new Province(2, "Shanghai");
		Province JiangXiProvince = new Province(3, "Jiangxi");
		Province HaiNanProvince = new Province(4, "Hainan");
		provinceMap.put("BeiJing", BeiJingProvince);
		provinceMap.put("Shanghai", ShangHaiProvince);
		provinceMap.put("Jiangxi", JiangXiProvince);
		provinceMap.put("Hainan", HaiNanProvince);

		// Beijing:
		List<City> BeiJingCities = new ArrayList<City>();
		BeiJingCities.add(new City(101, "East city district"));
		BeiJingCities.add(new City(102, "West City district"));
		BeiJingCities.add(new City(103, "Haiding"));
		BeiJingCities.add(new City(104, "Chaoyang"));
		BeiJingCities.add(new City(105, "Shijingshan]"));
		BeiJingCities.add(new City(106, "Fengtai"));
		BeiJingCities.add(new City(107, "Tongzhou"));
		BeiJingCities.add(new City(108, "Shunyi"));
		BeiJingCities.add(new City(109, "Changping"));
		BeiJingProvince.setCities(BeiJingCities);
		// Shanghai:
		List<City> ShangHaiCities = new ArrayList<City>();
		ShangHaiCities.add(new City(201, "Pudong"));
		ShangHaiCities.add(new City(202, "Xuhui"));
		ShangHaiCities.add(new City(203, "Changning"));
		ShangHaiCities.add(new City(204, "Putuo"));
		ShangHaiCities.add(new City(205, "Zhabei"));
		ShangHaiCities.add(new City(206, "Hongkou"));
		ShangHaiCities.add(new City(207, "Yangpu"));
		ShangHaiCities.add(new City(208, "Yangpu"));
		ShangHaiCities.add(new City(209, "Jingan"));
		ShangHaiProvince.setCities(ShangHaiCities);
		// Jiangxi:
		List<City> JiangXiCities = new ArrayList<City>();
		JiangXiCities.add(new City(301, "NanChang"));
		JiangXiCities.add(new City(302, "Jiujiang"));
		JiangXiCities.add(new City(303, "Xinyu"));
		JiangXiCities.add(new City(304, "Ganzhou"));
		JiangXiCities.add(new City(305, "Yichun"));
		JiangXiCities.add(new City(306, "Shangrao"));
		JiangXiCities.add(new City(307, "Jian"));
		JiangXiCities.add(new City(308, "Fuzhou"));
		JiangXiCities.add(new City(309, "Jingdezheng"));
		JiangXiProvince.setCities(JiangXiCities);
		// Hainan:
		List<City> HaiNanCities = new ArrayList<City>();
		HaiNanCities.add(new City(401, "Haikou"));
		HaiNanCities.add(new City(402, "Sanya"));
		HaiNanCities.add(new City(403, "Danzhou"));
		HaiNanCities.add(new City(404, "Wuzhishan"));
		HaiNanCities.add(new City(405, "Wenchang"));
		HaiNanCities.add(new City(406, "Qionghai"));
		HaiNanCities.add(new City(407, "Wanning"));
		HaiNanCities.add(new City(408, "Dongfang"));
		HaiNanCities.add(new City(409, "Dingan"));
		HaiNanProvince.setCities(HaiNanCities);
	}

	public static Map<String, Province> getProvinceMap() {
		return provinceMap;
	}
}
