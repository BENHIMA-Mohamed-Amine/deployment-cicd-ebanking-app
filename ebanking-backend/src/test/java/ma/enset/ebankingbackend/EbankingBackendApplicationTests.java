package ma.enset.ebankingbackend.services;

import ma.enset.ebankingbackend.dtos.CustomerDTO;
import ma.enset.ebankingbackend.entities.Customer;
import ma.enset.ebankingbackend.exceptions.CustomerNotFoundException;
import ma.enset.ebankingbackend.mappers.BankAccountMapperImpl;
import ma.enset.ebankingbackend.repositories.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BankAccountMapperImpl dtoMapper;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    void shouldSaveCustomer() {
        // 1. Arrange (Prepare data)
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("Hassan");
        customerDTO.setEmail("hassan@gmail.com");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Hassan");
        customer.setEmail("hassan@gmail.com");

        // Mock the mapper and repository behavior
        Mockito.when(dtoMapper.fromCustomerDTO(any(CustomerDTO.class))).thenReturn(customer);
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Mockito.when(dtoMapper.fromCustomer(any(Customer.class))).thenReturn(customerDTO);

        // 2. Act (Call the method)
        CustomerDTO result = bankAccountService.saveCustomer(customerDTO);

        // 3. Assert (Verify the result)
        Assertions.assertNotNull(result);
        Assertions.assertEquals("Hassan", result.getName());
    }

    @Test
    void shouldGetCustomer() throws CustomerNotFoundException {
        // 1. Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("Yassine");

        CustomerDTO expectedDTO = new CustomerDTO();
        expectedDTO.setId(customerId);
        expectedDTO.setName("Yassine");

        // Mock repository finding the customer
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        Mockito.when(dtoMapper.fromCustomer(customer)).thenReturn(expectedDTO);

        // 2. Act
        CustomerDTO result = bankAccountService.getCustomer(customerId);

        // 3. Assert
        Assertions.assertEquals(customerId, result.getId());
        Assertions.assertEquals("Yassine", result.getName());
    }
}