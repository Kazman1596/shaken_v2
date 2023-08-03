import org.example.dao.JdbcAccountDao;
import org.example.model.Account;
import org.example.model.RegisterUserDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

public class JdbcAccountDaoTests extends BaseDaoTests {
    private RegisterUserDto testAccount;

    private Account matchingAccount = new Account(15, "Test", "User", "test@test.com", "testuser", "$2a$10$Qv79d3G7m3yhQYdWf.V.teyQsSiAZiBMBgnNePb0J/hfKKR27URrm", "USER");
    private JdbcAccountDao sut;

    @Before
    public void setup() throws SQLException {
        sut = new JdbcAccountDao(dataSource);
        testAccount = new RegisterUserDto("Test", "User", "testuser6@test.com", "testuser66", "testuser6");
    }

    @Test
    public void getAccounts_returns_correct() {
        List<Account> results = sut.getAccounts();

        Assert.assertEquals(results.size(), 4);
    }

    @Test
    public void getAccountById_returns_correct() {
        Account account = sut.getAccountById(15);

        assertAccountsMatch(account, matchingAccount);
    }

    @Test
    public void getAccountById_returns_null_if_not_found() {
        Account account = sut.getAccountById(1000);
        Assert.assertNull(account);
    }

    @Test
    public void searchAccounts_returns_correctly() {
        List<Account> accounts = sut.searchAccounts("test");
        Assert.assertEquals(accounts.size(), 2);
    }

    @Test
    public void searchAccounts_returns_correctly_mixed_casing() {
        List<Account> accounts = sut.searchAccounts("tEsT");
        Assert.assertEquals(accounts.size(), 2);
    }

    @Test
    public void searchAccounts_returns_none_if_not_found() {
        List<Account> accounts = sut.searchAccounts("chupacabra");
        Assert.assertEquals(accounts.size(), 0);
    }

    @Test
    public void getAccountByUsername_should_return_account() {
        Account account = sut.getAccountByUsername("testuser");

        assertAccountsMatch(account, matchingAccount);
    }

    @Test
    public void getAccountByUsername_should_return_null_if_doesnt_exist() {
        Account account = sut.getAccountByUsername("chupacabra");

        Assert.assertNull(account);
    }

    @Test
    public void createAccount_returns_new_account() {
        Account newAccount = sut.createAccount(testAccount);

        int newId = newAccount.getId();
        Assert.assertTrue(newId > 0);

        Account retrievedAccount = sut.getAccountById(newId);
        assertAccountsMatch(newAccount, retrievedAccount);
    }

    @Test
    public void updateAccount_returns_correct_values() {
        Account accountToUpdate = sut.getAccountById(15);
        accountToUpdate.setFirstName("John");
        accountToUpdate.setLastName("Johnson");
        accountToUpdate.setEmail("johnnyjohnston@jj.com");
        accountToUpdate.setUsername("jjj");

        sut.updateAccount(accountToUpdate);

        Account retrievedAccount = sut.getAccountById(15);
        assertAccountsMatch(accountToUpdate, retrievedAccount);
    }

    @Test
    public void deleted_account_cant_be_retrieved() {

        sut.deleteAccount(15);

        Account retrievedAccount = sut.getAccountById(15);
        Assert.assertNull(retrievedAccount);
    }

    private void assertAccountsMatch(Account expected, Account result) {
        Assert.assertEquals(expected.getId(), result.getId());
        Assert.assertEquals(expected.getFirstName(), result.getFirstName());
        Assert.assertEquals(expected.getLastName(), result.getLastName());
        Assert.assertEquals(expected.getEmail(), result.getEmail());
        Assert.assertEquals(expected.getUsername(), result.getUsername());
        Assert.assertEquals(expected.getPassword(), result.getPassword());
        Assert.assertEquals(expected.getAuthorities(), result.getAuthorities());
    }
}
