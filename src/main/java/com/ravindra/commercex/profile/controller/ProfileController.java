package com.ravindra.commercex.profile.controller;


import com.ravindra.commercex.profile.dto.ChangePasswordRequest;
import com.ravindra.commercex.profile.dto.UpdateProfileRequest;
import com.ravindra.commercex.profile.dto.ProfileResponse;
import com.ravindra.commercex.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {


    private final ProfileService profileService;


    @GetMapping
    public ResponseEntity<ProfileResponse> getMyProfile(){

        return ResponseEntity.ok(
            profileService.getMyProfile()
        );

    }


    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
        @Valid
        @RequestBody UpdateProfileRequest request
    ){

        return ResponseEntity.ok(
            profileService.updateProfile(request)
        );

    }



    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
        @Valid
        @RequestBody ChangePasswordRequest request
    ){

        profileService.changePassword(request);


        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build();

    }

}
