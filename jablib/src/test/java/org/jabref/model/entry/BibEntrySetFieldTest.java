package org.jabref.model.entry;

import org.jabref.model.entry.field.Field;
import org.jabref.model.entry.field.FieldFactory;
import org.jabref.model.entry.field.StandardField;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BibEntrySetFieldTest {

    // T1
    @Test
    public void testT1_validRequiredField_validValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.AUTHOR, "Smith, John");
        assertEquals("Smith, John", entry.getField(StandardField.AUTHOR).orElse(null));
    }

    // T2
    @Test
    public void testT2_validRequiredField_malformedValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.YEAR, "202A"); // malformed year
        assertEquals("202A", entry.getField(StandardField.YEAR).orElse(null));
    }

    // T3
    @Test
    public void testT3_validRequiredField_emptyValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.AUTHOR, "");
        assertTrue(entry.getField(StandardField.AUTHOR).isEmpty());
    }

    // T4
    @Test
    public void testT4_validRequiredField_latexValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.AUTHOR, "\\alpha and \\beta");
        assertEquals("\\alpha and \\beta", entry.getField(StandardField.AUTHOR).orElse(null));
    }

    // T5
    @Test
    public void testT5_validOptionalField_validValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.NOTE, "Some note");
        assertEquals("Some note", entry.getField(StandardField.NOTE).orElse(null));
    }

    // T6
    @Test
    public void testT6_validOptionalField_malformedValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.NOTE, "202A");
        assertEquals("202A", entry.getField(StandardField.NOTE).orElse(null));
    }

    // T7
    @Test
    public void testT7_validOptionalField_emptyValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.NOTE, "");
        assertTrue(entry.getField(StandardField.NOTE).isEmpty());
    }

    // T8
    @Test
    public void testT8_validOptionalField_latexValue() {
        BibEntry entry = new BibEntry();
        entry.setField(StandardField.NOTE, "\\alpha and \\beta");
        assertEquals("\\alpha and \\beta", entry.getField(StandardField.NOTE).orElse(null));
    }

    // T9
    @Test
    public void testT9_invalidField_validValue() {
        BibEntry entry = new BibEntry();
        Field custom = FieldFactory.parseField("xyz");
        entry.setField(custom, "Valid");
        assertEquals("Valid", entry.getField(custom).orElse(null));
    }

    // T10
    @Test
    public void testT10_invalidField_malformedValue() {
        BibEntry entry = new BibEntry();
        Field custom = FieldFactory.parseField("xyz");
        entry.setField(custom, "202A");
        assertEquals("202A", entry.getField(custom).orElse(null));
    }

    // T11
    @Test
    public void testT11_invalidField_emptyValue() {
        BibEntry entry = new BibEntry();
        Field custom = FieldFactory.parseField("xyz");
        entry.setField(custom, "");
        assertTrue(entry.getField(custom).isEmpty());
    }

    // T12
    @Test
    public void testT12_invalidField_latexValue() {
        BibEntry entry = new BibEntry();
        Field custom = FieldFactory.parseField("xyz");
        entry.setField(custom, "\\alpha");
        assertEquals("\\alpha", entry.getField(custom).orElse(null));
    }

    // T13
    @Test
    public void testT13_nullField_validValue() {
        BibEntry entry = new BibEntry();
        assertThrows(NullPointerException.class, () -> {
            entry.setField(null, "Smith, John");
        });
    }

    // T14
    @Test
    public void testT14_nullField_malformedValue() {
        BibEntry entry = new BibEntry();
        assertThrows(NullPointerException.class, () -> {
            entry.setField(null, "202A");
        });
    }

    // T15
    @Test
    public void testT15_nullField_emptyValue() {
        BibEntry entry = new BibEntry();
        assertThrows(NullPointerException.class, () -> {
            entry.setField(null, "");
        });
    }

    // T16
    @Test
    public void testT16_nullField_latexValue() {
        BibEntry entry = new BibEntry();
        assertThrows(NullPointerException.class, () -> {
            entry.setField(null, "\\alpha");
        });
    }
}