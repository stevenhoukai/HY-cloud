package com.yyicbc.service;

import com.yyicbc.beans.RetData;
import com.yyicbc.beans.business.VO.InterfaceConfigurationVO;
import com.yyicbc.beans.querycondition.InterfaceConfigurationQuestVO;

import java.util.List;

public interface InterfaceConfigurationService {

    RetData save(InterfaceConfigurationVO interfaceConfigurationVO);

    RetData delete(InterfaceConfigurationQuestVO questVO);

    RetData getData(InterfaceConfigurationQuestVO questVO);

    RetData update(InterfaceConfigurationVO interfaceConfigurationVO);
}
