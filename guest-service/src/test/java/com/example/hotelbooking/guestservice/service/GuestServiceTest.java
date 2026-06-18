package com.example.hotelbooking.guestservice.service;

import com.example.hotelbooking.guestservice.dto.GuestRequest;
import com.example.hotelbooking.guestservice.dto.GuestResponse;
import com.example.hotelbooking.guestservice.exception.ResourceNotFoundException;
import com.example.hotelbooking.guestservice.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class GuestServiceTest {

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    void createGuest_shouldPersistGuest() {
        GuestRequest request = new GuestRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumber("+1111111111");
        request.setDocumentNumber("DOC-100");

        GuestResponse response = guestService.createGuest(request);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(guestRepository.count()).isEqualTo(1);
    }

    @Test
    void getGuestById_whenMissing_shouldThrow() {
        assertThatThrownBy(() -> guestService.getGuestById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
