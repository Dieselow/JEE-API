package fr.esgi.jee.api;

import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TimeSlotServiceTest {

    @InjectMocks
    private TimeSlotServiceImpl timeSlotService;
    private TimeSlot timeSlot;

    @Before
    public void init() {
        timeSlot = TimeSlot.builder()
                .id("1")
                .startDate(10)
                .endDate(20)
                .seats(4)
                .reservation(null)
                .build();
    }

    @Test
    public void testTimeSlotEndDateConflict() {
        TimeSlot t = TimeSlot.builder()
                .id("1")
                .startDate(4)
                .endDate(12)
                .seats(4)
                .reservation(null)
                .build();
        List<TimeSlot> list = new ArrayList<>();
        list.add(t);

        boolean isTimeSlotConflict = this.timeSlotService.isReservationConflict(timeSlot, list);
        Assert.assertTrue(isTimeSlotConflict);
    }

    @Test
    public void testTimeSlotStartDateConflict() {
        TimeSlot t = TimeSlot.builder()
                .id("1")
                .startDate(17)
                .endDate(23)
                .seats(4)
                .reservation(null)
                .build();
        List<TimeSlot> list = new ArrayList<>();
        list.add(t);

        boolean isTimeSlotConflict = this.timeSlotService.isReservationConflict(timeSlot, list);
        Assert.assertTrue(isTimeSlotConflict);
    }

    @Test
    public void testReservationOutOfTimeSlotRange() {
        TimeSlot t = TimeSlot.builder()
                .id("1")
                .startDate(5)
                .endDate(23)
                .seats(4)
                .reservation(null)
                .build();
        List<TimeSlot> list = new ArrayList<>();
        list.add(t);

        boolean isTimeSlotConflict = this.timeSlotService.isReservationConflict(timeSlot, list);
        Assert.assertTrue(isTimeSlotConflict);
    }

    @Test
    public void testReservationInTimeSlotRange() {
        TimeSlot t = TimeSlot.builder()
                .id("1")
                .startDate(13)
                .endDate(17)
                .seats(4)
                .reservation(null)
                .build();
        List<TimeSlot> list = new ArrayList<>();
        list.add(t);

        boolean isTimeSlotConflict = this.timeSlotService.isReservationConflict(timeSlot, list);
        Assert.assertTrue(isTimeSlotConflict);
    }

    @Test
    public void testReservationWithNoConflict() {
        TimeSlot t = TimeSlot.builder()
                .id("1")
                .startDate(4)
                .endDate(7)
                .seats(4)
                .reservation(null)
                .build();
        List<TimeSlot> list = new ArrayList<>();
        list.add(t);

        boolean isTimeSlotConflict = this.timeSlotService.isReservationConflict(timeSlot, list);
        Assert.assertFalse(isTimeSlotConflict);
    }
}
