package cn.itcast.service;

import java.util.List;

import cn.itcast.dao.SalDao;
import cn.itcast.domain.database.Sal;

public class SalService {
	private SalDao dao = new SalDao();
	private Integer salLikePageSize = 5;

	public List<Sal> queryAllSals() {
		return dao.queryAll();
	}

	/**
	 * 根据Id删除某条记录(Delete one record according to id)
	 * 
	 * @param id
	 * @return
	 */
	public Integer deleteById(Integer id) {
		return dao.deleteById(id);
	}

	/**
	 * 保存或更新薪水信息（Save or update salary information）
	 * 
	 * @param department
	 * @return
	 */
	public Integer saveOrUpdate(Sal sal) {
		return dao.saveOrUpdate(sal);
	}

	/**
	 * 保存薪水信息(Save salary information)
	 * 
	 * @param department
	 * @return
	 */
	public Integer Save(Sal sal) {
		return dao.Save(sal);
	}

	/**
	 * 查找所有薪水的最大Id(Query maximum salary id)
	 * 
	 * @return
	 */
	public Integer queryMaxId() {
		return dao.queryMaxId();
	}

	/**
	 * 模糊查询(Like query)
	 * 
	 * @return 模糊查询的薪水集(A collection of salaries)
	 */
	public List<Sal> LikeQueryByPage(Integer pageIndex, String keyWord) {
		List<Sal> salList = dao.LikeQueryByPage(pageIndex,
				salLikePageSize, keyWord);
		return salList;
	}

	/**
	 * 模糊查询要展示的页数(Like query pages)
	 * 
	 * @param keyWord
	 *            模糊查询的关键字
	 * @return 页数
	 */
	public Integer LikeQuerySize(String keyWord) {
		Integer count = dao.LikeQuerySize(keyWord);
		Integer pageCount = count == null ? 0 : (int) Math.ceil(count * 1.0d
				/ salLikePageSize);
		return pageCount;
	}
	
}
