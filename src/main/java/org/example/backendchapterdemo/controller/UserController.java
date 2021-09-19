package org.example.backendchapterdemo.controller;

import org.example.backendchapterdemo.dto.request.UserRequest;
import org.example.backendchapterdemo.dto.response.UserResponse;
import org.example.backendchapterdemo.service.UserService;
import org.example.backendchapterdemo.util.CsvMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user", produces = "application/json")
public class UserController {

    private final UserService userService;
    private final CsvMapperUtil csvMapperUtil;

    @Autowired
    public UserController(UserService userService, CsvMapperUtil csvMapperUtil) {
        this.userService = userService;
        this.csvMapperUtil = csvMapperUtil;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<UserResponse>> getUserPage(@PageableDefault Pageable pageable,
                                                          @RequestBody(required = false) UserRequest userRequest) {
        return ResponseEntity.ok(userService.getUserPage(userRequest, pageable));
    }

    @GetMapping("/export/csv")
    public void exportUserCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"users.csv\"");
        csvMapperUtil.exportListAsCsv(userService.getAllUsers(), response.getWriter());
    }


    @PostMapping(value = "/import/csv")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<UserResponse>> importUserCsv(@RequestPart("file") MultipartFile file) {
        List<UserResponse> users = userService
                .saveBulk(csvMapperUtil.importCsvAsList(file, UserRequest.class));
        return ResponseEntity.status(users.isEmpty() ? HttpStatus.NOT_MODIFIED : HttpStatus.OK).body(users);
    }

    @PostMapping("/")
    @Valid
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID userId) {
        Optional<UserResponse> userResponse = userService.deleteUser(userId);
        return userResponse
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID userId,
                                                   @RequestBody UserRequest userRequest) {
        Optional<UserResponse> userResponse = userService.updateUser(userId, userRequest);
        return userResponse
                .map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
