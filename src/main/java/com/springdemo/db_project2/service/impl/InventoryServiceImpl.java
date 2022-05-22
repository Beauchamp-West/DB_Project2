package com.springdemo.db_project2.service.impl;

import com.springdemo.db_project2.entity.Inventory;
import com.springdemo.db_project2.dao.InventoryDao;
import com.springdemo.db_project2.service.InventoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
     * @return 对象列表
     */
    @Override
    public List<Inventory> queryAll() {
        return this.inventoryDao.queryAll();
    }

    @Override
    public Long getNeverSoldProductCount() {
        return (Long) inventoryDao.selectNeverSoldCnt().get("cnt");
    }

    @Override
    public String getNeverSoldCountFormat() {
        return "Q9 " + getNeverSoldProductCount();
    }

    @Override
    public List<Map<String, Object>> getFavoriteProductModel() {
        return inventoryDao.selectFavorite();
    }

    @Override
    public String getFavoriteFormat() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Q10");
        List<Map<String, Object>> favorites = getFavoriteProductModel();
        for (Map<String, Object> map : favorites) {
            String model = (String) map.get("product_model");
            Long quantity = (Long) map.get("maximum");
            sj.add(String.format("%-35s%-10d", model, quantity));
        }
        return sj.toString();
    }

    @Override
    public List<Map<String, Object>> getAvgStockByCenter() {
        return inventoryDao.selectAvgStockByCenter();
    }

    @Override
    public String getAvgFormat() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Q11");
        List<Map<String, Object>> averages = getAvgStockByCenter();
        for (Map<String, Object> map : averages) {
            String center = (String) map.get("supply_center");
            BigDecimal average = (BigDecimal) map.get("average");
            sj.add(String.format("%-50s%-10s", center, average.toString()));
        }
        return sj.toString();
    }

    @Override
    public List<Map<String, Object>> getProductByNumber(String productNum) {
        return inventoryDao.selectProductByNumber(productNum);
    }

    /**
     * 新增数据
     *
     * @param inventory 实例对象
     * @return 实例对象
     */
    @Override
    public String insert(Inventory inventory) {
        this.inventoryDao.insert(inventory);
        return "Successfully inserted " + inventory.toString() + "!\n";
    }

    /**
     * 通过单次销量，指定企业和产品型号更新库存
     *
     * @param sold number of sales once
     * @param quantity
     * @param sales
     * @param model product_model
     * @param enterprise enterprise name
     * @return 更新信息
     */
    @Override
    public String updateBySold(Integer sold, Integer quantity, Integer sales, String model, String enterprise) {
        this.inventoryDao.updateBySold(sold, quantity, sales, model, enterprise);
        return "Successfully updated 1 inventory!\n";
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

    @Override
    public Inventory selectByEnterpriseAndModel(String name, String model) {
        List<Inventory> inventories = inventoryDao.selectByEnterpriseAndModel(name, model);
        if (inventories.size() == 0) return new Inventory();
        return inventories.get(0);
    }
}