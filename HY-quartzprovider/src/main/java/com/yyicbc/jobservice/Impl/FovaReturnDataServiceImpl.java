package com.yyicbc.jobservice.Impl;


import com.yyicbc.beans.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.PO.CompensationBPO;
import com.yyicbc.beans.business.PO.CompensationPO;
import com.yyicbc.beans.business.PO.UserImportPO;
import com.yyicbc.beans.business.VO.FovaUpdateStatVO;
import com.yyicbc.beans.enums.BillTypeEnums;
import com.yyicbc.beans.enums.FileStatusEnums;
import com.yyicbc.beans.enums.StatusEnums;
import com.yyicbc.beans.imports.PO.FormatFieldPO;
import com.yyicbc.beans.imports.PO.FovaUpdateDataPO;
import com.yyicbc.beans.imports.PO.TxtColumnFieldPO;
import com.yyicbc.beans.imports.PO.TxtTitleFieldPO;
import com.yyicbc.beans.querycondition.UserImportQuestVO;
import com.yyicbc.beans.utils.CCustomDate;
import com.yyicbc.beans.utils.YyStringUtils;
import com.yyicbc.beans.utils.distribute.method.BeanUtil;
import com.yyicbc.beans.utils.distribute.method.StringFormatUtils;
import com.yyicbc.dao.*;
import com.yyicbc.service.FovaReturnDataService;
import com.yyicbc.utils.FileUtil;
import com.yyicbc.utils.FovaUtils.BMVerifySignOpStep;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.yyicbc.utils.FileUtil.multipartFileToFile;
import static com.yyicbc.utils.FileUtil.unZipFiles;

@Service
@Slf4j
public class FovaReturnDataServiceImpl implements FovaReturnDataService {

    @Override
    public void updateFovaData(Map paramMap) throws BusinessException {

    }

    @Override
    public void saveReturnFovaData() throws BusinessException {

    }

    @Override
    public RetData updateFovaDataTest(UserImportPO request) {
        return null;
    }

    @Override
    public RetData updateFovaDataTest(Long compensationId) {
        return null;
    }

    @Override
    public RetData fovaReturnFile(UserImportQuestVO request) {
        return null;
    }

    @Override
    public void updateComBByFovaSrcData(List<FovaUpdateDataPO> returnList) throws Exception {

    }
}
