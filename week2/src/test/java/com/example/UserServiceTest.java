package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void testMockingAndStubbing() {
        UserRepository repository = mock(UserRepository.class);

        when(repository.findNameById(1)).thenReturn("Rahul");

        UserService service = new UserService(repository);

        String result = service.getUserName(1);

        assertEquals("Rahul", result);
    }

    @Test
    void testVerifyingInteraction() {
        UserRepository repository = mock(UserRepository.class);

        UserService service = new UserService(repository);

        service.getUserName(1);

        verify(repository).findNameById(1);
    }
}