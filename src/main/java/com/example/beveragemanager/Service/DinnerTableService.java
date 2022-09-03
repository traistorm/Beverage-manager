package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.DinnerTableDTO;
import com.example.beveragemanager.DTO.StaffDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.DinnerTable;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.Reponsitory.DinnerTableReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DinnerTableService {
    @Autowired
    DinnerTableReponsitory dinnerTableReponsitory;
    @Autowired
    UserService userService;
    public ResponseEntity<DinnerTableDTO> findAll(String token, Integer page, Integer itemPerPage)
    {
        try
        {
            UserDTO userDTO = userService.login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                DinnerTableDTO dinnerTableDTO = new DinnerTableDTO();
                List<DinnerTable> dinnerTableList = dinnerTableReponsitory.findAll();
                if (page != null && itemPerPage != null)
                {
                    List<DinnerTable> dinnerTableListReturn = dinnerTableReponsitory.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("dinnertableid").ascending())).getContent();
                    dinnerTableDTO.setMaxPage(dinnerTableList.size());
                    dinnerTableDTO.setDinnerTableList(dinnerTableListReturn);
                }
                else
                {
                    dinnerTableDTO.setMaxPage(dinnerTableList.size());
                    dinnerTableDTO.setDinnerTableList(dinnerTableList);
                }
                return new ResponseEntity<>(dinnerTableDTO, HttpStatus.OK);
            }
            else if (userDTO.getResult().equals("Token timeout"))
            {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
            else
            {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }

        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
    public DinnerTable findByDinnertableid(String dinnerTableID)
    {
        return dinnerTableReponsitory.findByDinnertableid(dinnerTableID);
    }
}
