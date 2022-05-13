package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Inventory;
import com.springdemo.db_project2.dao.InventoryDao;
import com.springdemo.db_project2.service.InventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Inventory)表服务实现类
 *
 * @author makejava
 * @since 2022-05-13 02:46:41
 */
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {
    @Resource
    private InventoryDao inventoryDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Inventory queryById(Integer id) {
        return this.inventoryDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Inventory> queryAllByLimit(int offset, int limit) {
        return this.inventoryDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    @Override
    public Inventory insert(Inventory inventory) {
        this.inventoryDao.insert(inventory);
        return inventory;
    }

    /**
     * 修改数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    @Override
    public Inventory update(Inventory inventory) {
        this.inventoryDao.update(inventory);
        return this.queryById(inventory.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.inventoryDao.deleteById(id) > 0;
    }
}