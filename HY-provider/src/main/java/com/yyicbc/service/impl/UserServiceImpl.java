package com.yyicbc.service.impl;


import com.yyicbc.Exception.BusinessException;
import com.yyicbc.beans.RetData;
import com.yyicbc.beans.RetResult;
import com.yyicbc.beans.StatusCode;
import com.yyicbc.beans.business.PO.CompanyBasePO;
import com.yyicbc.beans.querycondition.UserQueryVO;
import com.yyicbc.beans.security.*;
import com.yyicbc.component.pagehelper.JpaPageHelper;
import com.yyicbc.component.pagehelper.PageInfo;
import com.yyicbc.dao.*;
import com.yyicbc.service.UserService;
import com.yyicbc.utils.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/***
 * @author stv
 * 用户管理接口实现类，切面事务管理
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String adminSalt = "1234567890";

    private static final String defaultPwd = "123456";

//    private static final int sessionExpireTime = 1800;

    private static final List<String> codes = new CopyOnWriteArrayList<String>();

//    private static final String SECRETKEY = "yonyouICBC";

    @Value("${commons.page.size}")
    private Integer Common_page_size;

//    @Value("${redis.session.redisusersessionkey}")
//    private String redisUserSessionKey = "redisUserSessionKey";

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SysUserDao sysUserDao;

    @Autowired
    SysUserTokenDao sysUserTokenDao;

    @Autowired
    SysRoleUserDao sysRoleUserDao;

    @Autowired
    SysRoleMenuDao sysRoleMenuDao;

    @Resource
    CompanyFileDao companyFileDao;

    //获取验证码
//    @Override
//    public String getCode() {
//        CreateImageCode vCode = new CreateImageCode(116,36,4,10);
//        String code = vCode.getCode();
//        return code;
//    }
//
    //条件查询用户列表
    @Override
    public RetData all(UserQueryVO vo) throws BusinessException {
        Integer page = vo.getPage();
        RetData retData = new RetData();
        RetResult retResult = new RetResult();
        try {
            List<SysUserVO> allList = sysUserDao.findAll(new Specification<SysUserVO>() {
                //查询条件拼接过程
                @Override
                public Predicate toPredicate(Root root,
                                             CriteriaQuery criteriaQuery,
                                             CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<>();
                    if (!StringUtils.isBlank(vo.getUserCode())) {
                        Predicate codePredicate = cb.like(root.get("userCode").as(String.class), "%" + vo.getUserCode() + "%");
                        predicates.add(codePredicate);
                    }
                    if (!StringUtils.isBlank(vo.getUserName())) {
                        Predicate namePredicate = cb.like(root.get("userName").as(String.class), "%" + vo.getUserName() + "%");
                        predicates.add(namePredicate);
                    }
                    if (!StringUtils.isBlank(vo.getMobile())) {
                        Predicate mobilePredicate = cb.like(root.get("mobile").as(String.class), "%" + vo.getMobile() + "%");
                        predicates.add(mobilePredicate);
                    }
                    //判断结合中是否有数据
                    if (predicates.size() == 0) {
                        return null;
                    }
                    //将集合转化为CriteriaBuilder所需要的Predicate[]
                    Predicate[] predicateArr = new Predicate[predicates.size()];
                    predicateArr = predicates.toArray(predicateArr);

                    // 返回所有获取的条件： 条件 and 条件 and 条件
                    return cb.and(predicateArr);

                }
            });
            JpaPageHelper pagehelper = new JpaPageHelper();
            final List<PageInfo> pageInfos = pagehelper.SetStartPage(allList, page, Common_page_size);
            PageInfo pageInfo = pageInfos.get(0);
            retResult.setPage(pageInfo.getPageNow()).setPage_count(pageInfo.getTotlePage())
                    .setPage_size(pageInfo.getPgaeSize()).setTotal_count(allList.size())
                    .setItem_list(pageInfo.getList());
            retData.setCode(StatusCode.OK).setResult(retResult).setMsg("查询成功");
        } catch (Exception e) {
            throw new BusinessException(StatusCode.ERROR, e.getMessage());
        }
        return retData;
    }

    @Override
    public RetData saveOrUpdate(SysUserVO user) throws BusinessException {
        if (user.getUserId() == null) {
            String encPassWord = encoder.encode(defaultPwd);
            user.setSalt(adminSalt);
            user.setPassword(encPassWord);
        }
        RetData retData = new RetData();
        SysUserVO oldvo = sysUserDao.findSysUserVOByUserId(user.getUserId());
        if (oldvo != null) {
            user.setCreateTime(oldvo.getCreateTime());
            user.setCreateUserId(oldvo.getCreateUserId());
            if (oldvo.getUserCode().equals("admin") || oldvo.getUserCode().equals("root")) {
                retData.setCode(StatusCode.ERROR);
                retData.setMsg("初始用户admin和root不允许修改信息");
                return retData;
            }
        }
        SysUserVO savedVO = sysUserDao.save(user);
        if (savedVO.getUserId() != null) {
            retData.setCode(StatusCode.OK);
            retData.setMsg("保存用户成功");
        } else {
            retData.setCode(StatusCode.ERROR);
            retData.setMsg("保存用户失败");
        }
        return retData;
    }

    @Override
    public RetData deleteUserById(Long userId) throws BusinessException {
        RetData retData = new RetData();
        if (userId.intValue() == 1 || userId.intValue() == 2) {
            retData.setCode(StatusCode.ERROR);
            retData.setMsg("不允许删除初始用户");
            return retData;
        }
        sysUserDao.deleteById(userId);
        sysRoleUserDao.deleteSysUserRoleVOSByUserId(userId);
        retData.setCode(StatusCode.OK);
        retData.setMsg("删除用户成功");
        return retData;
    }

    @Override
    public RetData resetPwdById(Long userId) throws BusinessException {
//        String newpwd = "123456";
        StringBuffer codeBuffer = new StringBuffer();
        Random ra = new Random();
        for (int i = 0; i <= 5; i++) {
            codeBuffer.append(ra.nextInt(10));
        }
        SysUserVO uservo = sysUserDao.findSysUserVOByUserId(userId);
        RetData retData = new RetData();
        uservo.setPassword(encoder.encode(codeBuffer.toString()));
        sysUserDao.save(uservo);
        retData.setCode(StatusCode.OK);
        retData.setMsg("密码重置成功，新密码为: {" + codeBuffer.toString() + "}");
        return retData;
    }


    /**
     * @param usercode
     * @param password
     * @return 后台验证通过往sys_user_token插入一条数据，并将token返回给客户端
     * sys_user_token未过期数据表示在线用户，防止不同客户端同时登录一个用户问题
     */
    @Override
    public RetData execUserLogin(String usercode, String password) throws BusinessException {

        RetData result = new RetData();
//        String encPassWord = Md5Utils.encryptPassword(password, adminSalt);
        SysUserVO uservo = sysUserDao.findSysUserVOByUserCode(usercode);
        //对密码加密处理然后匹配
        if (uservo == null) {
            result.setCode(StatusCode.LOGINERROR);
            result.setMsg("系统中不存在该用户!");
        } else {
//            String lockObj = getLockObject(usercode);
            //usercode.intern()会从常量池拿对象
//            synchronized (usercode.intern()) {
            if (uservo.getStatus() == 0) {
                result.setCode(StatusCode.ERROR);
                result.setMsg("用户已被禁用!");
                return result;
            }
            if (!encoder.matches(password, uservo.getPassword())) {
                result.setCode(StatusCode.PWDERROR);
                result.setMsg("密码错误!");
            } else {
                //这里用户名与密码匹配正确，需要判断用户是否在线
                //如果用户没在线就放行登录成功，如果用户在线就返回错误信息无法登录
//                    boolean isOnline = checkUserisOnline(uservo.getUserId());
//                    if (isOnline) {
//                        result.setCode(1);
//                        result.setMsg("用户已在线！");
//                        result.setResult(new RetResult().setItem_list(uservo));
//                    } else {
//                        //登录成功，并将新的token信息插入到token表中
//                        SysUserTokenVO newVO = new SysUserTokenVO();
//                        String token = IdGen.uuid();
//                        newVO.setToken(token).setUserId(uservo.getUserId()).
//                                setUpdateTime(new Date()).setExpireTime(new Date(((new Date()).getTime() + expireTime)));
//                        sysUserTokenDao.save(newVO);
//                        List<String> menus = queryMenusByUserId(uservo.getUserId());
//                        result.setCode(0);
//                        result.setMsg("ok");
//                        result.setResult(new RetResult().setItem_list(uservo.setToken(token).setMenus(menus)));
//                    }
                //此处需要先判断用户是否已经在线，如果在线不允许重复登录TODO
                //登录成功需要生成jwt返回给客户端存储，以后系统的每次请求都需要带上token信息，服务端拦截器需要判断token是否过期决定
                //是否放行
//                    boolean isOnline = checkUserisOnline(uservo.getUserCode());
//                    if (isOnline) {
//                        result.setCode(StatusCode.USERONLINE);
//                        result.setMsg("用户已在线！");
//                        result.setResult(new RetResult().setItem_list(uservo));
//                        return result;
//                    }


                if (!encoder.matches(password, uservo.getPassword())) {
                    result.setCode(StatusCode.PWDERROR);
                    result.setMsg("密码错误!");
                } else {
                    List<String> menus = queryMenusByUserId(uservo.getUserId());
                    String jwt = jwtUtil.createJWT(uservo.getUserId().toString(), uservo.getUserCode(), menus, uservo.getUsertype());
                    result.setCode(StatusCode.OK);
                    result.setMsg("succuss");
                    Map<String, Object> retMap = new HashMap<String, Object>();
                    retMap.put("userId", uservo.getUserId());
                    retMap.put("userCode", uservo.getUserCode());
                    retMap.put("userName", uservo.getUserName());
                    retMap.put("corpId", uservo.getCorpId());
                    CompanyBasePO corpVO = companyFileDao.findCompanyBasePOById(Long.parseLong(uservo.getCorpId()));
                    retMap.put("corpCode", corpVO == null ? "" : corpVO.getCompanyEncode());
                    retMap.put("token", jwt);
                    retMap.put("userType", uservo.getUsertype());
                    retMap.put("menus", menus);
                    result.setResult(new RetResult().setItem_list(retMap));
                }
//                    redisTemplate.opsForValue().setIfAbsent(redisUserSessionKey+":"+uservo.getUserCode(), JsonUtils.objectToJson(uservo));
//                    redisTemplate.expire(redisUserSessionKey+":"+uservo.getUserCode(),sessionExpireTime, TimeUnit.SECONDS);
                //整合上面两个命令的原子性很重要，上面两行命令执行会有永久不失效的风险,将会话信息保存在redis并同时设置key值的过期时间
//                    boolean setSessionFlag = setIfAbsent(redisUserSessionKey + ":" + uservo.getUserCode(), JsonUtils.objectToJson(uservo), sessionExpireTime);
//                    if(!setSessionFlag){
//                        throw new BusinessException(StatusCode.EXCEPTIONERROR,"session设置发生错误，请检查redis服务器!");
//                    }
            }
//            }
        }
        return result;
    }


    /**
     * @param usercode
     * @param password
     * @return 后台验证通过往sys_user_token插入一条数据，并将token返回给客户端
     * sys_user_token未过期数据表示在线用户，防止不同客户端同时登录一个用户问题
     */
    @Override
    public RetData execUserLoginsso(String usercode, String password) throws BusinessException {

        RetData result = new RetData();
        SysUserVO uservo = sysUserDao.findSysUserVOByUserCode(usercode);
        //对密码加密处理然后匹配
        if (uservo == null) {
            result.setCode(StatusCode.LOGINERROR);
            result.setMsg("系统中不存在该用户!");
        } else {
            if (uservo.getStatus() == 0) {
                result.setCode(StatusCode.ERROR);
                result.setMsg("用户已被禁用!");
                return result;
            }

            if (password.equals("ssologin")) {
                List<String> menus = queryMenusByUserId(uservo.getUserId());
                String jwt = jwtUtil.createJWT(uservo.getUserId().toString(), uservo.getUserCode(), menus, uservo.getUsertype());
                result.setCode(StatusCode.OK);
                result.setMsg("succuss");
                Map<String, Object> retMap = new HashMap<String, Object>();
                retMap.put("userId", uservo.getUserId());
                retMap.put("userCode", uservo.getUserCode());
                retMap.put("userName", uservo.getUserName());
                retMap.put("corpId", uservo.getCorpId());
                CompanyBasePO corpVO = companyFileDao.findCompanyBasePOById(Long.parseLong(uservo.getCorpId()));
                retMap.put("corpCode", corpVO == null ? "" : corpVO.getCompanyEncode());
                retMap.put("token", jwt);
                retMap.put("userType", uservo.getUsertype());
                retMap.put("menus", menus);
                result.setResult(new RetResult().setItem_list(retMap));
            }
        }
        return result;
    }

//    public boolean setIfAbsent(final String key, final Serializable value, final long exptime) {
//        Boolean b = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
//                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
//                Object obj = connection.execute("set", keySerializer.serialize(key),
//                        valueSerializer.serialize(value),
//                        SafeEncoder.encode("NX"),
//                        SafeEncoder.encode("EX"),
//                        Protocol.toByteArray(exptime));
//                return obj != null;
//            }
//        });
//        return b;
//    }


    private List<String> queryMenusByUserId(Long userId) throws BusinessException {
        List<String> menus = new ArrayList<String>();
        Set<String> uniqueMenus = new HashSet<String>();
        List<SysUserRoleVO> userRolelist = sysRoleUserDao.findSysUserRoleVOSByUserId(userId);
        if (userRolelist != null && userRolelist.size() > 0) {
            for (int i = 0; i < userRolelist.size(); i++) {
                Long roleId = userRolelist.get(i).getRoleId();
                List<SysRoleMenuVO> roleMenus = sysRoleMenuDao.findSysRoleMenuVOSByRoleId(roleId);
                if (roleMenus != null && roleMenus.size() > 0) {
                    for (int j = 0; j < roleMenus.size(); j++) {
                        String menuKey = roleMenus.get(j).getMenuCode();
                        uniqueMenus.add(menuKey);
                    }
                }
            }
        }
        if (uniqueMenus != null && uniqueMenus.size() > 0) {
            Iterator<String> iterator = uniqueMenus.iterator();
            while (iterator.hasNext()) {
                String menuKey = iterator.next();
                menus.add(menuKey);
            }
        }
        return menus;
    }

    /**
     * @param userCode
     * @return 这里涉及到并发，有可能多个客户端同时登录
     */
//    private boolean checkUserisOnline(String userCode) {
////        SysUserTokenVO tokenVO = sysUserTokenDao.findAllByUserId(userId);
////        if (tokenVO == null) {
////            return false;
////        } else {
////            Date expireTime = tokenVO.getExpireTime();
////            Date now = new Date();
////            //如果当前服务器时间大于过期时间说明已经过期，否则就是在线
////            if (now.getTime() > expireTime.getTime()) {
////                //此时需要先将作废的token信息删除
////                sysUserTokenDao.deleteById(tokenVO.getId());
////                return false;
////            } else {
////                return true;
////            }
////        }
//        String userJson = (String) redisTemplate.opsForValue().get(redisUserSessionKey + ":" + userCode);
//        //判断是否为空
//        if (!StringUtils.isBlank(userJson)) {
//            return true;
//        }
//        return false;
//    }

    /**
     * @param userCode
     * @return 后台验证通过将sys_user_token中的对应数据删除，并将处理成功结果返回给客户端
     * 客户端拿到结果将本地token信息删除即可
     */
    @Override
    public synchronized RetData execUserLogout(String userCode) throws BusinessException {
        RetData result = new RetData();
//        SysUserVO userVO = sysUserDao.findSysUserVOByUserCode(usercode);
//        sysUserTokenDao.deleteByUserIdAndToken(userVO.getUserId(), token);
//        redisTemplate.delete(redisUserSessionKey + ":" + userCode);
        result.setCode(StatusCode.OK);
        result.setMsg("logoutok");
        return result;
    }

    @Override
    public RetData saveOrUpdate(ChangePwdVO vo) throws BusinessException {
        RetData result = new RetData();
        Long userId = vo.getUserId();
        String oldUserPassword = vo.getOldUserPassword();
        SysUserVO uservo = sysUserDao.findSysUserVOByUserId(userId);
        if (!encoder.matches(oldUserPassword, uservo.getPassword())) {
            result.setCode(StatusCode.PWDERROR);
            result.setMsg("原密码错误！");
        } else {
            String encNewPassWord = encoder.encode(vo.getNewUserPassword());
            uservo.setPassword(encNewPassWord);
//            saveOrUpdate(uservo);
            sysUserDao.save(uservo);
            result.setCode(StatusCode.OK);
            result.setMsg("密码修改成功！");
        }
        return result;
    }


    //    @Cacheable
    @Override
    public RetData findAllCorps() throws BusinessException {
        List<CompanyBasePO> all = companyFileDao.findAll();
        RetData retData = new RetData();
        retData.setCode(20000).setResult(new RetResult().setItem_list(all));
        return retData;
    }


    private synchronized String getLockObject(String usercode) throws BusinessException {
        String lockObj = usercode;
        if (!codes.contains(usercode)) {
            codes.add(usercode);
        } else {
            for (Iterator iter = codes.iterator(); iter.hasNext(); ) {
                String next = (String) iter.next();
                if (next.equals(usercode)) {
                    lockObj = next;
                }
            }
        }
        return lockObj;
    }

//    @Override
//    public RetData update(SysUserVO user) {
//        RetData retData = new RetData();
//        SysUserVO updatedVO = sysUserDao.save(user);
//        if(savedVO.getUserId()!=null){
//            retData.setCode(0);
//        }else{
//            retData.setCode(1);
//        }
//        return retData;
//    }
//
//    @Override
//    public Object save(User user) {
//
//
//
//        return null;
//    }
//
//    @Override
//    public Object delete(Long id) {
//        return null;
//    }
//
//    @Override
//    public YyResult checkCode(String type, String value, HttpServletRequest request) {
//        YyResult result = new YyResult();
//        HttpSession session = request.getSession();
//        Object codeobj = null;
//        if("1".equals(type)){
//            codeobj = session.getAttribute("busicode");
//        }else{
//            codeobj = session.getAttribute("admincode");
//        }
//        if(codeobj==null){
//            result.setStatus(1);
//            result.setMsg("验证码已过期，请刷新！");
//        }else{
//            String code = (String) codeobj;
//            if(value.length() >= 4 && code.toUpperCase() != value.toUpperCase()){
//                result.setStatus(1);
//                result.setMsg("验证码错误！");
//            }else{
//                result.setStatus(0);
//                result.setMsg("");
//            }
//
//        }
//        return result;
//    }


//    @Override
//    public YyResult queryUser(String usercode, String password, String type) {
//
//        YyResult result = new YyResult();
//        //初始用户的处理
//        if(usercode.toLowerCase().equals("admin")&&type.equals("1")){
//            String encPassWord = Md5Utils.encryptPassword(password, adminSalt);
//            SysUserVO user = sysUserDao.findSysUserVOByUserCodeAndUsertype(usercode, Integer.parseInt(type));
//            if(encPassWord.equals(user.getPassword())){
//                result.setStatus(0);
//                result.setMsg("ok");
//                result.setData(user);
//                return result;
//            }else{
//                result.setStatus(2);
//                result.setMsg("密码错误！");
//            }
//        }
//
//        SysUserVO uservo = sysUserDao.findSysUserVOByUserCodeAndUsertype(usercode, Integer.parseInt(type));
//        //对密码加密处理然后匹配
//        if(uservo==null){
//            result.setStatus(1);
//            result.setMsg("用户名不存在！");
//        }else{
//            if(!uservo.getPassword().equals(password)){
//                result.setStatus(2);
//                result.setMsg("密码错误！");
//            }else{
//                result.setStatus(0);
//                result.setMsg("ok");
//                result.setData(uservo);
//            }
//        }
//        return result;
//    }

}
