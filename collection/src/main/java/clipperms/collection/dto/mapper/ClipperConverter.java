package clipperms.collection.dto.mapper;

import clipperms.collection.dto.clipper.ClipperCreateRequest;
import clipperms.collection.dto.clipper.ClipperNoSeriesResponse;
import clipperms.collection.dto.clipper.ClipperWithSeriesResponse;
import clipperms.collection.model.Clipper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

//todo improve mapper functionalities (move to builder pattern)
@RequiredArgsConstructor
@Component
@Slf4j
public class ClipperConverter {


    private final SeriesConverter seriesConverter;


    //Convert a clipperCreateRequest to clipper model -> assign null for series id. It gets taken from DTO in controller.create method -> service class creates references if dto.getSeriesId != null
    public Clipper convertClipperCreateToModel(ClipperCreateRequest clipperDTO, MultipartFile imageFile) throws IOException {
        log.info("Converting clipperCreateRequest to clipper model: {}", clipperDTO);
        if(imageFile != null){
            return new Clipper((clipperDTO.getId() != null && !clipperDTO.getId().isEmpty() ? UUID.fromString(clipperDTO.getId()) : null), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), imageFile.getBytes(), UUID.fromString(clipperDTO.getCreatedBy()));
        }else{
            return new Clipper((clipperDTO.getId() != null && !clipperDTO.getId().isEmpty() ? UUID.fromString(clipperDTO.getId()) : null), clipperDTO.getName(), null, clipperDTO.getSeriesNumber(), UUID.fromString(clipperDTO.getCreatedBy()));
        }
    }

    //convert a Clipper model to a ClipperWithSeriesRequest
    public ClipperWithSeriesResponse convertModelToClipperWithSeriesResponse(Clipper clipper){
        log.info("Converting clipper model to clipperWithSeriesRequest: {}", clipper);
        return new ClipperWithSeriesResponse(clipper.getId().toString(), clipper.getName(), seriesConverter.convertModelToResponseNoClipper(clipper.getSeries()), clipper.getSeriesNumber(), clipper.getCreatedBy().toString(), Base64.getEncoder().encodeToString(clipper.getImageData()));
    }

    public ClipperNoSeriesResponse convertModelToClipperNoSeriesRequest(Clipper clipper){
        log.info("Converting clipper model to clipperNoSeriesRequest: {}", clipper);
        return new ClipperNoSeriesResponse(clipper.getId().toString(), clipper.getName(), clipper.getSeries().getName(), clipper.getSeriesNumber(), clipper.getCreatedBy().toString(), Base64.getEncoder().encodeToString(clipper.getImageData()));
    }

    public ClipperNoSeriesResponse convertModelNoSeriesToClipperNoSeries(Clipper clipper){
        log.info("Converting clipper model to clipperNoSeriesRequest: {}", clipper);
        return new ClipperNoSeriesResponse(clipper.getId().toString(), clipper.getName(), null, clipper.getSeriesNumber(), clipper.getCreatedBy().toString(), Base64.getEncoder().encodeToString(clipper.getImageData()));
    }
}
