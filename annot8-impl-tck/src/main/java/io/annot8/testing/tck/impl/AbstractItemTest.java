package io.annot8.testing.tck.impl;

import io.annot8.common.data.content.Text;
import io.annot8.core.data.Content;
import io.annot8.core.data.Item;
import io.annot8.core.exceptions.IncompleteException;
import io.annot8.core.exceptions.UnsupportedContentException;
import io.annot8.testing.testimpl.TestConstants;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Abstract class providing tests for non default methods on provided implementations of {@link Item}
 */
public abstract class AbstractItemTest {

    protected abstract Item getItem();

    @Test
    public void testGetParent() {

    }

    @Test
    public void testCreateChildItem() {
        Item item = getItem();
        Item childItem = item.createChildItem();
        assertNotNull(childItem);
        assertTrue(childItem.hasParent());
        assertEquals(item.getId(), childItem.getParent().get());
    }

    @Test
    public void testGetContent() {
        Item item = getItem();
        try {
             item.create(Text.class)
                     .withData(() -> "test")
                     .withName(TestConstants.CONTENT_NAME)
                     .withId(TestConstants.CONTENT_ID)
                     .save();
        } catch (IncompleteException |UnsupportedContentException e) {
            fail("Test should not fail here", e);
        }

        Optional<Content<?>> optional = item.getContent(TestConstants.CONTENT_ID);
        assertTrue(optional.isPresent());
        Content<?> content = optional.get();
        assertEquals(TestConstants.CONTENT_ID, content.getId());
        assertEquals(TestConstants.CONTENT_NAME, content.getName());
        assertEquals("test", content.getData());
    }

    @Test
    public void testGetContents() {
        Item item = getItem();

        try {
            item.create(Text.class).withData(() -> "test").withName(TestConstants.CONTENT_NAME).save();
            item.create(Text.class).withData(() -> "test2").withName("content2").save();
        } catch (UnsupportedContentException | IncompleteException e) {
            fail("Test should not error here",e );
        }

        assertThat(item.getContents().map(Content::getData).map(String.class::cast))
                .containsExactlyInAnyOrder("test", "test2");
        assertThat(item.getContents().map(Content::getName))
                .containsExactlyInAnyOrder(TestConstants.CONTENT_NAME, "content2");
    }

    @Test
    public void testCreate() {
        Item item = getItem();

        Text test = null;
        try {
            test = item.create(Text.class).withName(TestConstants.CONTENT_NAME).withData("test").save();
        } catch (UnsupportedContentException | IncompleteException e) {
            fail("Test should not throw an exception.", e);
        }

        assertNotNull(test);
        assertEquals(TestConstants.CONTENT_NAME, test.getName());
        assertEquals("test", test.getData());
        assertNotNull(test.getId());
    }

    @Test
    public void testRemoveContent() {
        Item item = getItem();
        try {
            item.create(Text.class)
                    .withData(() -> "test")
                    .withName(TestConstants.CONTENT_NAME)
                    .withId(TestConstants.CONTENT_ID)
                    .save();
        } catch (IncompleteException |UnsupportedContentException e) {
            fail("Test should not fail here", e);
        }

        assertThat(item.getContents()).isNotEmpty();

        item.removeContent(TestConstants.CONTENT_ID);

        assertThat(item.getContents()).isEmpty();
    }

    @Test
    public void testDiscard() {
        Item item = getItem();
        assertFalse(item.isDiscarded());
        item.discard();
        assertTrue(item.isDiscarded());
    }

}
