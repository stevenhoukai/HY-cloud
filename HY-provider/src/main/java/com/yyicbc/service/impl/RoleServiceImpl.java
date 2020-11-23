package com.yyicbc.service.impl;

import com.netflix.discovery.converters.Auto;
import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.YyResult;
import com.yyicbc.beans.querycondition.RoleQueryVO;
import com.yyicbc.beans.security.SysRoleMenuVO;
import com.yyicbc.beans.security.SysRoleVO;
import com.yyicbc.beans.security.SysUserRoleVO;
import com.yyicbc.beans.security.SysUserVO;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.SysRoleDao;
import com.yyicbc.dao.SysRoleMenuDao;
import com.yyicbc.dao.SysRoleUserDao;
import com.yyicbc.dao.SysUserDao;
import com.yyicbc.service.RoleService;
import com.yyicbc.service.UserService;
import com.yyicbc.utils.JsonUtils;
import com.yyicbc.utils.Md5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    SysRoleDao sysRoleDao;
    @Autowired
    SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    SysRoleUserDao sysRoleUserDao;


    @Value("${commons.page.size}")
    private Integer Common_page_size;

    @Override
    public RetData all(RoleQueryVO vo) throws BusinessException{

            Integer page = vo.getPage();
            RetData retData = new RetData();
            RetResult retResult = new RetResult();
            try {
                List<SysRoleVO> allList = sysRoleDao.findAll(new Specification<SysRoleVO>() {
                    //查询条件拼接过程
                    @Override
                    public Predicate toPredicate(Root root,
                                                 CriteriaQuery criteriaQuery,
                                                 CriteriaBuilder cb) {
                        List<Predicate> predicates = new ArrayList<>();
                        if (!StringUtils.isBlank(vo.getRoleCode())) {
                            Predicate codePredicate = cb.like(root.get("roleCode").as(String.class), "%" + vo.getRoleCode() + "%");
                            predicates.add(codePredicate);
                        }
                        if (!StringUtils.isBlank(vo.getRoleName())) {
                            Predicate namePredicate = cb.like(root.get("roleName").as(String.class), "%" + vo.getRoleName() + "%");
                            predicates.add(namePredicate);
                        }
                        if (predicates.size() == 0) {
                            return null;
                        }
                        Predicate[] predicateArr = new Predicate[predicates.size()];
                        predicateArr = predicates.toArray(predicateArr);
                        return cb.and(predicateArr);

                    }
                });
                if (allList != null && allList.size() > 0) {
                    for (SysRoleVO roleVO : allList) {
                        List<SysRoleMenuVO> sysRoleMenuVOS = sysRoleMenuDao.findSysRoleMenuVOSByRoleId(roleVO.getRoleId());
                        if (sysRoleMenuVOS != null && sysRoleMenuVOS.size() > 0) {
                            List<String> menus = new ArrayList<String>();
                            for (SysRoleMenuVO rmvo : sysRoleMenuVOS) {
                                menus.add(rmvo.getMenuCode());
                            }
                            roleVO.setMenus(menus);
                        }
                    }
                }

                JpaPageHelper pagehelper = new JpaPageHelper();
                final List<PageInfo> pageInfos = pagehelper.SetStartPage(allList, page, Common_page_size);
                PageInfo pageInfo = pageInfos.get(0);
                retResult.setPage(pageInfo.getPageNow()).setPage_count(pageInfo.getTotlePage())
                        .setPage_size(pageInfo.getPgaeSize()).setTotal_count(allList.size())
                        .setItem_list(pageInfo.getList());
                retData.setCode(StatusCode.OK).setResult(retResult);
            }catch (Exception e){
                throw new BusinessException(StatusCode.ERROR,e.getMessage());
            }
            return retData;

    }

    /**
     *
     * @param role
     * @return
     * 新增或者更新角色
     */
    @Override
    public RetData saveOrUpdate(SysRoleVO role) throws BusinessException{
        RetData retData = new RetData();
        SysRoleVO savedVO = sysRoleDao.save(role);
        if (savedVO.getRoleId() != null) {
            retData.setCode(StatusCode.OK);
        } else {
            retData.setCode(StatusCode.ERROR);
        }
        return retData;
    }

    @Override
    public RetData execPermission(String roleId, String menuString) throws BusinessException{
        RetData retData = new RetData();
        if(menuString==null){
            retData.setCode(StatusCode.OK);
            retData.setMsg("ok");
        }else{
            List<String> menus = JsonUtils.jsonToList(menuString, String.class);
            //TODO这里先逐一插入，后续需要优化成批处理
            sysRoleMenuDao.deleteSysRoleMenuVOSByRoleId(Long.parseLong(roleId));
            if(menus!=null&&menus.size()>0){
                for(int i = 0 ; i < menus.size() ; i++){
                    String menu = menus.get(i);
                    SysRoleMenuVO rmVO = new SysRoleMenuVO();
                    rmVO.setRoleId(Long.parseLong(roleId));
                    rmVO.setMenuCode(menu);
                    sysRoleMenuDao.save(rmVO);
                }
            }
            retData.setCode(StatusCode.OK);
            retData.setMsg("ok");
        }
        return retData;
    }

    @Override
    public RetData getAllRoleUserList(long roleId) throws BusinessException{
        RetData result = new RetData();
//        List<SysUserVO> allUsers = sysUserDao.findAll();
        //权限分配时的用户列表只能显示业务员
//        List<SysUserVO> allUsers = sysUserDao.findSysUserVOSByUsertype(0);
        List<SysUserVO> allUsers = sysUserDao.findSysUserVOSByUsertypeAndStatus(0,1);
        List<SysUserVO> allUsersManagers = sysUserDao.findSysUserVOSByUsertype(2);
        allUsers.addAll(allUsersManagers);
//        List<Integer> userTpes = new ArrayList<Integer>();
//        userTpes.add(0);
//        userTpes.add(2);
//        List<SysUserVO> allUsers = sysUserDao.findSysUserVOSByUsertypeIn(userTpes);
        List<SysUserRoleVO> UserRoleVOList = sysRoleUserDao.findSysUserRoleVOSByRoleId(roleId);
        List<Long> isRoledList = new ArrayList<Long>();
        if(UserRoleVOList!=null&&UserRoleVOList.size()>0){
            for(SysUserRoleVO urvo : UserRoleVOList){
                isRoledList.add(urvo.getUserId());
            }
        }
        if(allUsers!=null&&allUsers.size()>0){
            for(SysUserVO uservo : allUsers){
                if(isRoledList.contains(uservo.getUserId())){
                    uservo.setRolestatus(1);
                }else{
                    uservo.setRolestatus(0);
                }
            }
        }
        result.setCode(StatusCode.OK).setMsg("ok").setResult(new RetResult().setItem_list(allUsers));
        return result;
    }

    @Override
    public RetData execUserPermission(String roleId, String userIds) throws BusinessException{
        RetData retData = new RetData();
        if(userIds==null){
            retData.setCode(StatusCode.OK);
            retData.setMsg("ok");
        }else{
            List<String> userList = JsonUtils.jsonToList(userIds, String.class);
            sysRoleUserDao.deleteSysUserRoleVOSByRoleId(Long.parseLong(roleId));
            LinkedList<SysUserRoleVO> vos = new LinkedList<>();
            for(int i = 0 ; i < userList.size() ; i++){
                String userid = userList.get(i);
                SysUserRoleVO urVO = new SysUserRoleVO();
                urVO.setRoleId(Long.parseLong(roleId));
                urVO.setUserId(Long.parseLong(userid));
//                sysRoleUserDao.save(urVO);
                vos.add(urVO);
            }
            sysRoleUserDao.saveAll(vos);
            retData.setCode(StatusCode.OK);
            retData.setMsg("ok");
        }
        return retData;
    }


    @Override
    public RetData deleteRoleById(Long roleId) throws BusinessException{
        RetData retData = new RetData();
        SysRoleVO one = sysRoleDao.getOne(roleId);
        if(one.getRoleCode().equals("maker")||one.getRoleCode().equals("checker")){
              throw new BusinessException(StatusCode.ERROR,"该角色不允许删除");
        }
        sysRoleDao.deleteById(roleId);
        sysRoleUserDao.deleteSysUserRoleVOSByRoleId(roleId);
        sysRoleMenuDao.deleteSysRoleMenuVOSByRoleId(roleId);
        retData.setCode(StatusCode.OK);
        retData.setMsg("删除角色成功");
        return retData;
    }

}
