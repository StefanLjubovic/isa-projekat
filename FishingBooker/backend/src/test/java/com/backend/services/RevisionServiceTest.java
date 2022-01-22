package com.backend.services;

import com.backend.model.Revision;
import com.backend.repository.IRevisionRepository;
import com.backend.service.RevisionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static com.backend.constants.RevisionConstants.*;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class )
@SpringBootTest
public class RevisionServiceTest {

    @Mock
    private IRevisionRepository revisionRepositoryMock;

    @InjectMocks
    private RevisionService revisionService;

    @Test
    public void testGetAllPendingRevisions() {

        // 1. Definisanje ponasanja Mock objekta
        Revision revision1 = new Revision(DB_REVISION_CONTENT_1, DB_REVISION_MARK_1, DB_REVISION_IS_APPROVED_1, null);
        Revision revision2 = new Revision(DB_REVISION_CONTENT_2, DB_REVISION_MARK_2, DB_REVISION_IS_APPROVED_2, null);
        Revision revision3 = new Revision(DB_REVISION_CONTENT_3, DB_REVISION_MARK_3, DB_REVISION_IS_APPROVED_3, null);

        when(revisionRepositoryMock.findAllByOrderByIdAsc()).thenReturn(Arrays.asList(revision1, revision2, revision3));

        // 2. Akcija
        List<Revision> pendingRevisions = revisionService.getAllPendingRevisions();

        // 3. Verifikacija
        assertThat(pendingRevisions).hasSize(2);
        assertFalse(pendingRevisions.get(0).getApproved());
        assertFalse(pendingRevisions.get(1).getApproved());

        verify(revisionRepositoryMock, times(1)).findAllByOrderByIdAsc();
        verifyNoMoreInteractions(revisionRepositoryMock);

    }
}
