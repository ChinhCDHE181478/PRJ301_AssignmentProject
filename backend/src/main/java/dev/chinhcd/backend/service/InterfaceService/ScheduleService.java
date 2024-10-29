package dev.chinhcd.backend.service.InterfaceService;

import dev.chinhcd.backend.dtos.requests.ScheduleRequest;
import dev.chinhcd.backend.dtos.responses.ScheduleResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.ScheduleCampaign;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    List<ScheduleResponse> getAllSchedules();

    List<ScheduleResponse> searchSchedule(ScheduleRequest request);

    ScheduleResponse addSchedule(ScheduleRequest request) throws DataNotFoundException;

    ScheduleResponse updateSchedule(ScheduleRequest request) throws DataNotFoundException;

    void deleteSchedule(int id);

    Optional<ScheduleCampaign> getScheduleCampaign(int id);
}
