package edu.vsp.model;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * User model class.
 * The class has annotations for working with a csv file. The fields are bound by position.
 * Roles and phone numbers are stored in a csv file as a string with values separated by a specified delimiter.
 * On csv->bean mapping the string is split using the specified delimiter and map to the List.
 * On bean->csv mapping The list is joined into a string, separating the values using the specified delimiter.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

    @CsvBindByPosition(position = 0)
    private String name;
    @CsvBindByPosition(position = 1)
    private String surname;
    @CsvBindByPosition(position = 2)
    private String email;
    @CsvBindAndSplitByPosition(position = 3, elementType = String.class, splitOn = " ", writeDelimiter = " ")
    private List<String> roles;
    @CsvBindAndSplitByPosition(position = 4, elementType = String.class, splitOn = " ", writeDelimiter = " ")
    private List<String> phoneNumbers;
}
