package com.bks.eurosporttest.data.network.mapper

import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.domain.util.DomainMapper
import com.bks.eurosporttest.data.network.model.StoryDto
import com.bks.eurosporttest.util.DateUtils

class StoryDtoMapper : DomainMapper<StoryDto, Story> {

    override fun mapToDomainModel(model: StoryDto): Story {
        return Story(
            id = model.id,
            title = model.title,
            teaser = model.teaser,
            image = model.image,
            date = DateUtils.timestampToDate(model.date),
            author = model.author,
            sport = model.sport
        )
    }

    override fun mapFromDomainModel(domainModel: Story): StoryDto {
        return StoryDto(
            id = domainModel.id,
            title = domainModel.title,
            teaser = domainModel.teaser,
            image = domainModel.image,
            date = DateUtils.dateToTimestamp(domainModel.date),
            author = domainModel.author,
            sport = domainModel.sport
        )
    }

    fun toDomainList(initial: List<StoryDto>): List<Story> {
        return initial.map { mapToDomainModel(it) }
    }

    fun fromDomainList(initial: List<Story>): List<StoryDto> {
        return initial.map { mapFromDomainModel(it) }
    }
}