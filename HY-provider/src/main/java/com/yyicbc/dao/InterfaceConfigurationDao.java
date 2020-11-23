package com.yyicbc.dao;

import com.yyicbc.beans.business.PO.CompensationPO;
import com.yyicbc.beans.business.PO.InterfaceConfigurationPO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface InterfaceConfigurationDao extends JpaRepository<InterfaceConfigurationPO,Long>, JpaSpecificationExecutor<InterfaceConfigurationPO> {

    InterfaceConfigurationPO findInterfaceConfigurationPOById(Long configId);

    void deleteById(Long configId);

    void deleteInterfaceConfigurationPOById(Long configId);
}
