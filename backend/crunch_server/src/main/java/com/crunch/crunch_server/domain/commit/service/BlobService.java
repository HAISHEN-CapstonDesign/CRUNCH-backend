package com.crunch.crunch_server.domain.commit.service;

import com.crunch.crunch_server.domain.commit.dto.BlobDTO;
import com.crunch.crunch_server.domain.commit.dto.PostLineDetailDTO;
import com.crunch.crunch_server.domain.commit.dto.RecentCommitDTO;
import com.crunch.crunch_server.domain.commit.entity.Commits;
import com.crunch.crunch_server.domain.commit.entity.PostLineDetail;
import com.crunch.crunch_server.domain.commit.entity.PostModification;
import com.crunch.crunch_server.domain.commit.mapper.BlobMapper;
import com.crunch.crunch_server.domain.commit.mapper.CommitMapper;
import com.crunch.crunch_server.domain.commit.mapper.PostDetailMapper;
import com.crunch.crunch_server.domain.commit.repository.BlobRepository;
import com.crunch.crunch_server.domain.commit.repository.CommitDetailRepository;
import com.crunch.crunch_server.domain.commit.repository.ModifyPostModificationRepository;
import com.crunch.crunch_server.domain.crew.dto.WriterCrewDetailDTO;
import com.crunch.crunch_server.domain.crew.entity.WritersCrew;
import com.crunch.crunch_server.domain.crew.repository.WriterCrewRepository;
import com.crunch.crunch_server.domain.project.entity.Posts;
import com.crunch.crunch_server.domain.project.repository.PostRepository;
import com.crunch.crunch_server.domain.user.entity.User;
import com.crunch.crunch_server.domain.user.respository.UserRepository;
import com.crunch.crunch_server.domain.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlobService {

    @Autowired
    private BlobRepository repository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommitDetailRepository postDetailRepository;
    
    @Autowired
    private ModifyPostModificationRepository PMrepository;

    @Autowired
    private WriterCrewRepository writerCrewRepository;

    private String post_now;

    public BlobDTO getProjectBlob(RecentCommitDTO recentCommitDTO)
    {
        User user = userRepository.findByIdNumber(recentCommitDTO.getUserId());

        //find post detail list
        List<PostLineDetailDTO> lineDTO = getPostDetailList(recentCommitDTO);
        
        BlobDTO blobDTO = BlobMapper.Instance.toDTO(recentCommitDTO,user,lineDTO);
        
        post_now = null;
        post_now = blobDTO.getPost();

        return blobDTO;
    }


    public BlobDTO getProjectBlobWhenNewPostAndModifyingNow(int postId)
    {
        Posts post = postRepository.findByIds(postId);
        User otherUser = userRepository.findByIdNumber(post.getModifyingUserId());

        BlobDTO blobDTO = BlobMapper.Instance.toAddUserDTO(otherUser);

        post_now = null;
        post_now = blobDTO.getPost();

        return blobDTO;
    }

    public BlobDTO getProjectBlobWhenNotNewPostAndModifyingNow(RecentCommitDTO recentCommitDTO, int postId)
    {
        Posts post = postRepository.findByIds(postId);
        
        User user = userRepository.findByIdNumber(recentCommitDTO.getUserId());
        
        User otherUser = userRepository.findByIdNumber(post.getModifyingUserId());

        //find post detail list
        List<PostLineDetailDTO> lineDTO = getPostDetailList(recentCommitDTO);
        
        BlobDTO blobDTO = BlobMapper.Instance.toAddModifyingUserDTO(recentCommitDTO,user,otherUser,lineDTO);
       
        post_now = null;
        post_now = blobDTO.getPost();

        return blobDTO;
    }

    public RecentCommitDTO getRecentCommitInfo(int postId)
    {
        List<Commits> commits = repository.findByPostId(postId);
        int last  = commits.size() -1;

        RecentCommitDTO commitDTO = CommitMapper.Instance.toRecentDTO((Commits)commits.get(last));
        return commitDTO;
        
    }

    public int getSizeOfCommitList(int postId)
    {
        List<Commits> commits = repository.findByPostId(postId);
        return commits.size();
    }

    private List<PostLineDetailDTO> getPostDetailList(RecentCommitDTO recentCommitDTO) {
        int commitId = recentCommitDTO.getId();
        System.out.println(commitId);
        PostModification postModification = PMrepository.findByCommitId(commitId);
        System.out.println(recentCommitDTO.getPostId());
        List<PostLineDetail> postLineDetailList = postDetailRepository.findAllByIdOrderedByLineNum(recentCommitDTO.getPostId(),postModification.getAfterPostLength());
        List<PostLineDetailDTO> lineDTO = PostDetailMapper.Instance.toPostDetailListDTO(postLineDetailList);
        return lineDTO;
    }
    
    public List<WriterCrewDetailDTO> getWriterCrewNameList(int projectId) {

        List<WritersCrew> writersCrews = writerCrewRepository.findByWriterCrewIdentityProjectId(projectId);
        List<WriterCrewDetailDTO> writerCrewDetailDTOs = new ArrayList<WriterCrewDetailDTO>();
        for(int i = 0; i<writersCrews.size(); i++)
        {
            WriterCrewDetailDTO writerCrewDetailDTO = new WriterCrewDetailDTO();
            writerCrewDetailDTO.setWriterName(userRepository.findByIdNumber(writersCrews.get(i).getWriterCrewIdentity().getUserId()).getNickname());

            writerCrewDetailDTOs.add(writerCrewDetailDTO);
        }
		return writerCrewDetailDTOs;
	}

  
    /**
     * @return String return the post_now
     */
    public String getPost_now() {
        return post_now;
    }

    /**
     * @param post_now the post_now to set
     */
    public void setPost_now(String post_now) {
        this.post_now = post_now;
    }



}
