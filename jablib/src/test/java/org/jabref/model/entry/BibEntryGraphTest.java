package org.jabref.model.entry;

import java.lang.reflect.Method;
import java.util.Optional;

import org.jabref.model.entry.field.Field;
import org.jabref.model.entry.field.FieldFactory;
import org.jabref.model.entry.field.StandardField;
import org.jabref.model.entry.types.EntryType;
import org.jabref.model.entry.types.IEEETranEntryType;
import org.jabref.model.entry.types.StandardEntryType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BibEntryGraphTest {

    private Optional<?> invokeGetSourceField(String fieldName, EntryType targetType, EntryType sourceType) throws Exception {
        Field field = FieldFactory.parseField(fieldName);
        Method method = BibEntry.class.getDeclaredMethod("getSourceField", org.jabref.model.entry.field.Field.class, EntryType.class, EntryType.class);
        method.setAccessible(true);
        BibEntry entry = new BibEntry(); // instance needed to call method
        return (Optional<?>) method.invoke(entry, field, targetType, sourceType);
    }

    @Test
    public void testT1_forbiddenField_IDS_returnsEmpty() throws Exception {
        assertTrue(invokeGetSourceField("ids", StandardEntryType.InBook, StandardEntryType.Book).isEmpty());
    }

    @Test
    public void testT2_mvBookToInBook_author_returnsAuthor() throws Exception {
        assertEquals(StandardField.AUTHOR, invokeGetSourceField("author", StandardEntryType.InBook, StandardEntryType.MvBook).orElse(null));
    }

    @Test
    public void testT3_bookToBookInBook_bookAuthor_returnsAuthor() throws Exception {
        assertEquals(StandardField.AUTHOR, invokeGetSourceField("bookauthor", StandardEntryType.BookInBook, StandardEntryType.Book).orElse(null));
    }

    @Test
    public void testT4_mvBookToBook_maintitle_returnsTitle() throws Exception {
        assertEquals(StandardField.TITLE, invokeGetSourceField("maintitle", StandardEntryType.Book, StandardEntryType.MvBook).orElse(null));
    }

    @Test
    public void testT5_mvBookToBook_title_returnsEmpty() throws Exception {
        assertTrue(invokeGetSourceField("title", StandardEntryType.Book, StandardEntryType.MvBook).isEmpty());
    }

    @Test
    public void testT6_bookToBookInBook_booktitle_returnsTitle() throws Exception {
        assertEquals(StandardField.TITLE, invokeGetSourceField("booktitle", StandardEntryType.BookInBook, StandardEntryType.Book).orElse(null));
    }

    @Test
    public void testT7_mvBookToBook_shorttitle_returnsEmpty() throws Exception {
        assertTrue(invokeGetSourceField("shorttitle", StandardEntryType.Book, StandardEntryType.MvBook).isEmpty());
    }

    @Test
    public void testT8_periodicalToArticle_journaltitle_returnsTitle() throws Exception {
        assertEquals(StandardField.TITLE, invokeGetSourceField("journaltitle", StandardEntryType.Article, IEEETranEntryType.Periodical).orElse(null));
    }

    @Test
    public void testT9_bookToBookInBook_titleaddon_returnsEmpty() throws Exception {
        assertTrue(invokeGetSourceField("titleaddon", StandardEntryType.BookInBook, StandardEntryType.Book).isEmpty());
    }

    @Test
    public void testT10_unknownField_returnsOriginalField() throws Exception {
        Field result = (Field) invokeGetSourceField("unknownfield", StandardEntryType.Article, StandardEntryType.MvBook).orElse(null);
        assertNotNull(result);
        assertEquals("unknownfield", result.getName());
    }
}