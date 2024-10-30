package dev.chinhcd.backend.service;

import dev.chinhcd.backend.dtos.requests.ScheduleRequest;
import dev.chinhcd.backend.dtos.responses.ScheduleResponse;
import dev.chinhcd.backend.dtos.responses.ShiftResponse;
import dev.chinhcd.backend.exceptions.DataNotFoundException;
import dev.chinhcd.backend.models.PlanCampaign;
import dev.chinhcd.backend.models.ScheduleCampaign;
import dev.chinhcd.backend.models.Shift;
import dev.chinhcd.backend.repository.ScheduleRepository;
import dev.chinhcd.backend.repository.WorkerRepository;
import dev.chinhcd.backend.service.InterfaceService.ScheduleService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final EntityManager entityManager;
    private final ScheduleRepository scheduleRepository;
    private final CampaignServiceImpl campaignService;
    private final ShiftServiceImpl shiftService;
    private final WorkerRepository workerRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponse> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleCampaign -> ScheduleResponse.builder()
                .scheduleId(scheduleCampaign.getScheduleId())
                .date(scheduleCampaign.getDate())
                .quantity(scheduleCampaign.getQuantity())
                .campaignId(scheduleCampaign.getCampaign().getCampaignId())
                .shiftResponse(ShiftResponse.builder()
                        .shiftId(scheduleCampaign.getShift().getShiftId())
                        .shiftName(scheduleCampaign.getShift().getShiftName())
                        .shiftStart(scheduleCampaign.getShift().getShiftStart())
                        .shiftEnd(scheduleCampaign.getShift().getShiftEnd())
                        .build())
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleResponse> searchSchedule(ScheduleRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScheduleCampaign> cq = cb.createQuery(ScheduleCampaign.class);
        Root<ScheduleCampaign> scheduleCampaignRoot = cq.from(ScheduleCampaign.class);

        List<Predicate> predicates = new ArrayList<>();

        if (request.scheduleId() != null) {
            predicates.add(cb.equal(scheduleCampaignRoot.get("scheduleId"), request.scheduleId()));
        }

        if (request.campaignId() != null) {
            predicates.add(cb.equal(scheduleCampaignRoot.get("campaign").get("campaignId"), request.campaignId()));
        }

        if (request.date() != null) {
            predicates.add(cb.equal(scheduleCampaignRoot.get("date"), request.date()));
        }

        if(request.quantity() != null) {
            predicates.add(cb.equal(scheduleCampaignRoot.get("quantity"), request.quantity()));
        }

        if(request.shiftRequest() != null && request.shiftRequest().shiftId() != null) {
            predicates.add(cb.equal(scheduleCampaignRoot.get("shift").get("shiftId"), request.shiftRequest().shiftId()));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        List<ScheduleCampaign> resultList = entityManager.createQuery(cq).getResultList();

        return resultList.stream().map(scheduleCampaign -> ScheduleResponse.builder()
                .scheduleId(scheduleCampaign.getScheduleId())
                .date(scheduleCampaign.getDate())
                .quantity(scheduleCampaign.getQuantity())
                .campaignId(scheduleCampaign.getCampaign().getCampaignId())
                .shiftResponse(ShiftResponse.builder()
                        .shiftId(scheduleCampaign.getShift().getShiftId())
                        .shiftName(scheduleCampaign.getShift().getShiftName())
                        .shiftStart(scheduleCampaign.getShift().getShiftStart())
                        .shiftEnd(scheduleCampaign.getShift().getShiftEnd())
                        .build())
                .build()).toList();
    }

    @Transactional
    @Override
    public ScheduleResponse addSchedule(ScheduleRequest request) throws DataNotFoundException {

        PlanCampaign campaign = campaignService.getCampaignById(request.campaignId())
                .orElseThrow(() -> new DataNotFoundException("Campaign not found"));

        Shift shift = shiftService.getShift(request.shiftRequest().shiftId())
                .orElseThrow(() -> new DataNotFoundException("Shift not found"));

        ScheduleCampaign scheduleCampaign = scheduleRepository.save(ScheduleCampaign.builder()
                .campaign(campaign)
                .date(request.date())
                .shift(shift)
                .quantity(request.quantity())
                .build());

        return ScheduleResponse.builder()
                .scheduleId(scheduleCampaign.getScheduleId())
                .date(request.date())
                .quantity(request.quantity())
                .campaignId(scheduleCampaign.getCampaign().getCampaignId())
                .shiftResponse(ShiftResponse.builder()
                        .shiftId(scheduleCampaign.getShift().getShiftId())
                        .shiftName(scheduleCampaign.getShift().getShiftName())
                        .shiftStart(scheduleCampaign.getShift().getShiftStart())
                        .shiftEnd(scheduleCampaign.getShift().getShiftEnd())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public ScheduleResponse updateSchedule(ScheduleRequest request) throws DataNotFoundException {

        ScheduleCampaign schedule = scheduleRepository.findById(request.scheduleId())
                .orElseThrow(() -> new DataNotFoundException("Schedule not found"));

        PlanCampaign campaign = campaignService.getCampaignById(request.campaignId())
                .orElseThrow(() -> new DataNotFoundException("Campaign not found"));

        Shift shift = shiftService.getShift(request.shiftRequest().shiftId())
                .orElseThrow(() -> new DataNotFoundException("Shift not found"));

        schedule.setCampaign(campaign);
        schedule.setDate(request.date());
        schedule.setQuantity(request.quantity());
        schedule.setShift(shift);

        schedule = scheduleRepository.save(schedule);

        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .date(request.date())
                .quantity(request.quantity())
                .campaignId(schedule.getCampaign().getCampaignId())
                .shiftResponse(ShiftResponse.builder()
                        .shiftId(schedule.getShift().getShiftId())
                        .shiftName(schedule.getShift().getShiftName())
                        .shiftStart(schedule.getShift().getShiftStart())
                        .shiftEnd(schedule.getShift().getShiftEnd())
                        .build())
                .build();
    }

    @Transactional
    @Override
    public void deleteSchedule(int id) throws Exception {
        if(!workerRepository.findByScheduleId(id).isEmpty()) {
            throw new Exception("Cannot delete because it have children, you must delete children fist");
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public Optional<ScheduleCampaign> getScheduleCampaign(int id) {
        return scheduleRepository.findById(id);
    }
}
