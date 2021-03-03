package com.bks.eurosporttest.domain.data.network.mapper

import com.bks.eurosporttest.domain.model.Video
import com.bks.eurosporttest.domain.util.DomainMapper
import com.bks.eurosporttest.domain.data.network.model.VideoDto
import com.bks.eurosporttest.util.DateUtils

class VideoDtoMapper : DomainMapper<VideoDto, Video> {

    override fun mapToDomainModel(model: VideoDto): Video {
        return Video(
            id = model.id,
            title = model.title,
            thumb = model.thumb,
            url = model.url,
            date = DateUtils.longToDate(model.longDate),
            sport = model.sport,
            views = model.views
        )
    }

    override fun mapFromDomainModel(domainModel: Video): VideoDto {
        return VideoDto(
            id = domainModel.id,
            title = domainModel.title,
            thumb = domainModel.thumb,
            url = domainModel.url,
            longDate = DateUtils.dateToLong(domainModel.date),
            sport = domainModel.sport,
            views = domainModel.views
        )
    }

    fun toDomainList(initial: List<VideoDto>): List<Video>{
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Video>): List<VideoDto>{
        return initial.map { mapFromDomainModel(it) }
    }
}