package edu.vsp.repository.csv;

import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vsp.configuration.CsvConfiguration;
import edu.vsp.model.DuplicateEmailException;
import edu.vsp.model.User;
import edu.vsp.repository.UserRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class UserCsvRepository implements UserRepository {

    private CsvConfiguration csvConfiguration;

    public UserCsvRepository(CsvConfiguration csvConfiguration) {
        this.csvConfiguration = csvConfiguration;
    }

    private static Predicate<User> emailEqalityPredicate(String email) {
        return product -> email.equals(product.getEmail());
    }

    @Override
    public User save(User user){
        checkEmail(findAll(), user.getEmail());
        try (Writer writer = csvConfiguration.getWriter()) {
            StatefulBeanToCsv<User> beanWriter = csvConfiguration.getBeanToCsvWriter(writer);
            beanWriter.write(user);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private void checkEmail(List<User> users, String email) {
        boolean isEmailExist = users.stream().anyMatch(emailEqalityPredicate(email));
        if(isEmailExist){
            throw new DuplicateEmailException("User with such email already exists.");
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return findAll().stream()
                .filter(emailEqalityPredicate(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        try(Reader reader = csvConfiguration.getReader()) {
            users = csvConfiguration.getCsvToBeanReader(reader, User.class).parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User update(String email, User newUser) {
        List<User> users = findAll();

        User userForUpdate = users.stream()
                .filter(emailEqalityPredicate(email))
                .findFirst()
                .orElseThrow();

        if (!newUser.getEmail().isEmpty()){
            checkEmail(users, newUser.getEmail());
            userForUpdate.setEmail(newUser.getEmail());
        }
        if (!newUser.getName().isEmpty())
            userForUpdate.setName(newUser.getName());
        if (!newUser.getSurname().isEmpty())
            userForUpdate.setSurname(newUser.getSurname());
        if (!newUser.getRoles().isEmpty())
            userForUpdate.setRoles(newUser.getRoles());
        if (!newUser.getPhoneNumbers().isEmpty())
            userForUpdate.setPhoneNumbers(newUser.getPhoneNumbers());

        rewriteFile(users);

        return newUser;
    }

    private void rewriteFile(List<User> users) {
        Path usersTempPath = Path.of("src/main/resources/usertmp.csv");
        try{
            Writer writer = new FileWriter(usersTempPath.toFile(), false);
            StatefulBeanToCsv<User> beanWriter = csvConfiguration.getBeanToCsvWriter(writer);
            beanWriter.write(users);
            writer.close();
            Files.move(usersTempPath, csvConfiguration.getUserCsvFilePath(), StandardCopyOption.ATOMIC_MOVE);
        } catch (CsvDataTypeMismatchException | IOException | CsvRequiredFieldEmptyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        List<User> users = findAll();
        boolean removed = users.removeIf(emailEqalityPredicate(email));
        if (removed){
            rewriteFile(users);
        }
    }
}
