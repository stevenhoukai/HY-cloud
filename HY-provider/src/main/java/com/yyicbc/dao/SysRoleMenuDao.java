package com.yyicbc.dao;


        import com.yyicbc.beans.security.SysRoleMenuVO;
        import com.yyicbc.beans.security.SysUserTokenVO;
        import com.yyicbc.beans.security.SysUserVO;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
        import org.springframework.stereotype.Repository;

        import java.util.List;


/**
 * \
 * JpaSpecificationExecutor用于条件拼接
 * author:steven
 */
@Repository
public interface SysRoleMenuDao extends JpaRepository<SysRoleMenuVO, Long>, JpaSpecificationExecutor<SysRoleMenuVO> {


    public void deleteSysRoleMenuVOSByRoleId(Long roleId);

    public List<SysRoleMenuVO> findSysRoleMenuVOSByRoleId(Long roleId);

}