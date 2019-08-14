package com.caogang.service;

import com.caogang.dao.RoleDao;
import com.caogang.dao.UserDao;
import com.caogang.dao.UserToRoleDao;
import com.caogang.entity.QueryEntity;
import com.caogang.entity.RoleInfo;
import com.caogang.entity.UserInfo;
import com.caogang.utils.MD5;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: xiaogang
 * @date: 2019/8/12 - 15:05
 */
@Transactional
@Service
public class UserServiceImpl {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserToRoleDao userToRoleDao;

    public Page<UserInfo> selectAllUser(QueryEntity queryEntity){
        return userDao.findAllByUsernameLikeAndSexAndCreatedtimeBetween(queryEntity.getIname(),queryEntity.getIsex(),queryEntity.getStart(),queryEntity.getEnd(), PageRequest.of(queryEntity.getPage(),queryEntity.getPageSize(),Sort.by(Sort.Order.desc("createdtime"))));
    }

    public Page<UserInfo> selectAllUser1(String iname,Integer page,Integer pageSize){
        return userDao.findAllByUsernameLike(iname,PageRequest.of(page,pageSize,Sort.by(Sort.Order.desc("createdtime"))));
    }

    public Page<UserInfo> selectAllUser2(String inames, Integer isex, Integer page, Integer pageSize) {
        return userDao.findAllByUsernameLikeAndSex(inames,isex,PageRequest.of(page,pageSize,Sort.by(Sort.Order.desc("createdtime"))));
    }

    public Page<UserInfo> selectAllUser3(String inames, Date start, Date end, Integer page, Integer pageSize) {
        return userDao.findAllByUsernameLikeAndCreatedtimeBetween(inames,start,end,PageRequest.of(page,pageSize,Sort.by(Sort.Order.desc("createdtime"))));
    }

    public void deleteUser(String[] id) {
        userDao.deleteAll(userDao.findAllById(Arrays.asList(id)));
    }

    public void deleteUserToRole(String[] id) {
        userToRoleDao.deleteAll(userToRoleDao.findAllByUserId(Arrays.asList(id)));
    }

    public void addUser(UserInfo userInfo) {

        String password = MD5.encryptPassword(userInfo.getPassword(), "lcg");

        userInfo.setPassword(password);

        userDao.save(userInfo);
    }

    public void updateUser(UserInfo userInfo) {

        userInfo.setVersion(userDao.findById(userInfo.getId()).get().getVersion());

        String password = MD5.encryptPassword(userInfo.getPassword(), "lcg");

        userInfo.setPassword(password);

        userDao.save(userInfo);
    }

    public RoleInfo selectRole(String userId) {
        return roleDao.selectRole(userId);
    }

    private CellStyle getColumnTopStyle(Workbook workbook) {

        CellStyle cellStyle=workbook.createCellStyle();

        cellStyle.setBorderBottom(BorderStyle.THIN);

        cellStyle.setBorderLeft(BorderStyle.THIN);

        cellStyle.setBorderRight(BorderStyle.THIN);

        cellStyle.setBorderTop(BorderStyle.THIN);

        //设置自动换行
        cellStyle.setWrapText(false);

        cellStyle.setAlignment(HorizontalAlignment.CENTER);

        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        return cellStyle;

    }

    public void exportUserList(List<UserInfo> userList)throws IOException {

        Workbook workbook=new XSSFWorkbook();

        //设置表的牙样式
        CellStyle cellStyle=getColumnTopStyle(workbook);

        Sheet sheet=workbook.createSheet("用户列表");

        int index=0;

        Row row0=sheet.createRow(index++);

        //设置第一行
        row0.createCell(0).setCellValue("编号ID");
        row0.createCell(1).setCellValue("用户名");
        row0.createCell(2).setCellValue("登录名");
        row0.createCell(3).setCellValue("密码");
        row0.createCell(4).setCellValue("性别");
        row0.createCell(5).setCellValue("电话");
        row0.createCell(6).setCellValue("头像路径");
        row0.createCell(7).setCellValue("角色名称");
        row0.createCell(8).setCellValue("创建时间");
        row0.createCell(9).setCellValue("最后修改时间");
        row0.createCell(10).setCellValue("版本号");

        //把查询结果放入到对应的列
        for (UserInfo test:userList) {

            Row row=sheet.createRow(index++);

            row.createCell(0).setCellValue(test.getId());
            row.createCell(1).setCellValue(test.getUsername());
            row.createCell(2).setCellValue(test.getLoginname());
            row.createCell(3).setCellValue(test.getPassword());
            row.createCell(4).setCellValue(test.getSex()==1?"男":"女");
            row.createCell(5).setCellValue(test.getTel());
            row.createCell(6).setCellValue(test.getUrl());
            row.createCell(7).setCellValue(test.getRoleInfo().getRolename());
            row.createCell(8).setCellValue(test.getCreatedtime());
            row.createCell(9).setCellValue(test.getUpdatedtime());
            row.createCell(10).setCellValue(test.getVersion());

        }

        for(int m=0;m<=sheet.getLastRowNum();m++){

            Row rowStyle=sheet.getRow(m);

            for(int n=0;n<rowStyle.getLastCellNum();n++){

                rowStyle.getCell(n).setCellStyle(cellStyle);

            }
        }

        try {

            FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\93917\\Desktop\\user.xlsx");

            try {

                workbook.write(fileOutputStream);

            } catch (IOException e) {

                e.printStackTrace();

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }


    }


}
