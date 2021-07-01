package fr.esgi.jee.api.partner.domain.timeslot;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends MongoRepository<TimeSlot, String> {

    // @Query("{$or:[{$and:[{start_date:{$lt:?0}},{end_date:{$gt:?1}}, ]}, {$and:[{start_date:{$gt:?0}}, {end_date:{$gt:?1}}, {start_date:{$lt:?1}}, ]}, {$and:[{start_date:{$gt:?0}}, {end_date:{$lt:?1}}, ]}, {$and:[{start_date:{$lt:?0}},{end_date:{$lt:?1}},{end_date:{$gt:?0}}]}]}")
    @Query("{$or:[" +
            "{start_date: {$lt:?0}, end_date: {$gt:?1} }," +
            "{start_date: {$gt:?0}, end_date: {$gt:?1}, start_date:{$lt:?1} }," +
            "{start_date: {$gt:?0}, end_date: {$lt:?1} }," +
            "{start_date: {$lt:?0}, end_date: {$lt:?1}, end_date: {$gt:?0} }" +
            "]}")
    List<TimeSlot> findConflicting(long start_date, long end_date);
}
