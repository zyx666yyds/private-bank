package com.chinasoft.clientservice.repository;



import com.chinasoft.clientservice.pojo.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    // 查询所有
    List<Client> findAll();
    //  根据姓名查询
    Client findByName(String name);
    // 保存
    Client save(Client client);
    //  根据风级查询
    Client findByRiskLevel(String riskLevel);
    // 根据姓名删除
    Client deleteByName(String name);

    List<Client> findByNameLike(String name);


}
