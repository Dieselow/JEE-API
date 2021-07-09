package fr.esgi.jee.api.unit;

import fr.esgi.jee.api.partner.domain.timeslot.TimeSlot;
import fr.esgi.jee.api.partner.domain.timeslot.TimeSlotServiceImpl;
import fr.esgi.jee.api.partner.infra.dto.CreateTimeSlotRangeDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TimeSlotRangeTest {
    @InjectMocks
    private TimeSlotServiceImpl timeSlotService;

    @Test
    public void testRangeBuildTimeSlot() {
        CreateTimeSlotRangeDTO dto = CreateTimeSlotRangeDTO.builder()
                .startDate(1629484382)
                .endDate(1629494682)
                .seats(4)
                .duration(1000)
                .pause(60)
                .build();

        List<TimeSlot> slots = timeSlotService.buildTimeSlotByRange(dto);

        int index = 0;
        for (TimeSlot slot: slots) {
            Assert.assertTrue(slot.getStartDate() == dto.getStartDate() + (index * (dto.getDuration() + dto.getPause())));
            Assert.assertTrue(slot.getEndDate() == slot.getStartDate() + dto.getDuration() - 1);
            index++;
        }
    }

}
