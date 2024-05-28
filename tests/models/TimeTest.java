package models;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import core.models.Time;

public class TimeTest {
    @Test
    public void timeTest() {
        Time one = Time.of(60);
        Time two = Time.of(120);

        assertEquals(60, one.toSeconds());
        assertEquals(120, two.toSeconds());
        one.update(20);
        assertNotEquals(60, one.toSeconds());
        assertEquals(20, one.toSeconds());

        Time m = two.minus(one);
        assertEquals(100, m.toSeconds());

        assertEquals("1 minute and 40 seconds.", m.toString());

        m.update(m.add(Time.of(20)).toSeconds());
        assertEquals("2 minutes.", m.toString());

        Time n = m.clone();
        n.update(3600);
        assertEquals("1 hour.", n.toString());

        n.update(n.add(3620).toSeconds());
        assertEquals("2 hours and 20 seconds.", n.toString());

        n.update(7260);
        assertEquals("2 hours and 1 minute.", n.toString());

        n.update(n.add(60).toSeconds());
        assertEquals("2 hours and 2 minutes.", n.toString());

        n.update(3600+120);
        assertEquals("1 hour and 2 minutes.", n.toString());

        n.update(20);
        assertEquals("20 seconds.", n.toString());

        n.update(2*3600);
        assertEquals("2 hours.", n.toString());

        n.update(60);
        assertEquals("1 minute.", n.toString());

        n.update(1);
        assertEquals("1 second.", n.toString());

        n.update(7261);
        assertEquals("2 hours, 1 minute and 1 second.", n.toString());

        n.update(7322);
        assertEquals("2 hours, 2 minutes and 2 seconds.", n.toString());

        n.update(7262);
        assertEquals("2 hours, 1 minute and 2 seconds.", n.toString());

        n.update(7321);
        assertEquals("2 hours, 2 minutes and 1 second.", n.toString());
        
        assertEquals("2:2:1", n.toISOString());

        Time time = Time.of(50, 2, 1);
        assertEquals("50 hours, 2 minutes and 1 second.", time.toString());

        Time time1 = Time.of(1, 1, 1);
        assertEquals("1 hour, 1 minute and 1 second.", time1.toString());

        assertEquals("1 hour, 2 minutes and 1 second.", Time.of(1, 2, 1).toString());
        assertEquals("1 hour, 2 minutes and 2 seconds.", Time.of(1, 2, 2).toString());
        assertEquals("1 hour, 1 minute and 2 seconds.", Time.of(1, 1, 2).toString());
        assertEquals("1 hour and 1 minute.", Time.of(1, 1, 0).toString());
        assertEquals("2 hours and 1 second.", Time.of(2, 0, 1).toString());
        assertEquals("1 hour and 2 seconds.", Time.of(1, 0, 2).toString());
        assertEquals("1 hour and 1 second.", Time.of(1, 0, 1).toString());
        assertEquals("2 minutes and 2 seconds.", Time.of(0, 2, 2).toString());
        assertEquals("2 minutes and 1 second.", Time.of(0, 2, 1).toString());
        assertEquals("1 minute and 1 second.", Time.of(0, 1, 1).toString());
        assertEquals(".", Time.of(0, 0, 0).toString());

    }

    @Test
    public void maxTimeTest() {
        Time one = Time.of(60);
        one.setHours(Integer.MAX_VALUE);
        one.setMinutes(1);
        one.setSeconds(2);

        assertEquals(Integer.MAX_VALUE, one.toSeconds());
    }

    @Test
    public void maxTimeTest2() {
        Time one = Time.of(60);
        one.setHours(2);
        one.setMinutes(1);
        one.setSeconds(Integer.MAX_VALUE);

        assertEquals(Integer.MAX_VALUE, one.toSeconds());
    }

    @Test
    public void maxTimeTest3() {
        Time one = Time.of(60);
        one.setHours(1);
        one.setMinutes(Integer.MAX_VALUE);
        one.setSeconds(2);

        assertEquals(Integer.MAX_VALUE, one.toSeconds());
    }

    @Test
    public void maxTimeTest4() {
        Time one = Time.of("Infinity");

        assertEquals(Integer.MAX_VALUE, one.toSeconds());
    }
}
