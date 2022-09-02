package com.example.beveragemanager.Service;

import com.example.beveragemanager.DTO.BillDTO;
import com.example.beveragemanager.DTO.StaffDTO;
import com.example.beveragemanager.DTO.UserDTO;
import com.example.beveragemanager.Entiry.Bill;
import com.example.beveragemanager.Entiry.Staff;
import com.example.beveragemanager.Reponsitory.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    UserService userService;
    public ResponseEntity<StaffDTO> findAll(String token, Integer page, Integer itemPerPage)
    {
        try
        {
            UserDTO userDTO = userService.login(null, null, token);
            if (userDTO.getResult().equals("Token is valid"))
            {
                StaffDTO staffDTO = new StaffDTO();
                List<Staff> staffList = staffRepository.findAll();
                if (page != null && itemPerPage != null)
                {
                    List<Staff> staffListReturn = staffRepository.findAll(PageRequest.of(page - 1, itemPerPage, Sort.by("staffid").ascending())).getContent();
                    staffDTO.setMaxPage(staffList.size());
                    staffDTO.setStaffList(staffListReturn);
                }
                else
                {
                    staffDTO.setMaxPage(staffList.size());
                    staffDTO.setStaffList(staffList);
                }
                return new ResponseEntity<>(staffDTO, HttpStatus.OK);
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
    public Staff findByStaffid(String staffID)
    {
        return staffRepository.findByStaffid(staffID);
    }
}
