package ShowAndGame.ShowAndGame.Services;

import ShowAndGame.ShowAndGame.Persistence.Dto.GetTagDto;
import ShowAndGame.ShowAndGame.Persistence.Dto.TagForCreationAndUpdateDto;
import ShowAndGame.ShowAndGame.Persistence.Entities.Tag;
import ShowAndGame.ShowAndGame.Persistence.Repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {
    private final TagRepository tagRepository;
    @Autowired
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository;
    }

    public void Create(TagForCreationAndUpdateDto newTag) {
        Tag tagToCreate = new Tag();

        tagToCreate.setName(newTag.getName());
        tagToCreate.setColor(newTag.getColor());
        tagToCreate.setGames(new ArrayList<>());

        tagRepository.save(tagToCreate);
    }

    public void Delete(Long id) {
        tagRepository.deleteById(id);
    }

    public Optional<Tag> GetById(Long id) {
        return tagRepository.findById(id);
    }

    public List<GetTagDto> GetAll() {
        List<Tag> allTags = tagRepository.findAll();
        return allTags.stream()
                .map(GetTagDto::new)
                .collect(Collectors.toList());
    }

    public List<GetTagDto> GetTagsByGameId(Long gameId) {
        List<Tag> tagsByGame = tagRepository.findByGames_Id(gameId);
        return tagsByGame.stream()
                .map(GetTagDto::new)
                .collect(Collectors.toList());
    }

    public void Update(GetTagDto tagToUpdate) {
        Optional<Tag> currentTag = tagRepository.findById(tagToUpdate.getId());
        Tag tag = null;

        if(currentTag.isPresent()){
            tag = currentTag.get();
            tag.setName(tagToUpdate.getName());
            tag.setColor(tagToUpdate.getColor());
            tagRepository.save(tag);
        }
    }
}
