package chronoMod.cards;

import chronoMod.DefaultMod;
import chronoMod.actions.DejaVuAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.watcher.CrushJointsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static chronoMod.DefaultMod.makeCardPath;

public class DejaVu extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(DejaVu.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;
    private static AbstractCard lastCard = null;

    public DejaVu() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (lastCard != null) {
            DefaultMod.logger.info("DejaVu : last card :" + lastCard.cardID);
            AbstractCard card = lastCard.makeStatEquivalentCopy();
            if (card.costForTurn >= 0) {
                card.setCostForTurn(0);
            }
            this.addToBot(new DejaVuAction(card));
        } else {
            DefaultMod.logger.info("DejaVu : error : last card is null ");
        }
    }

    @Override
    public void applyPowers() {
        lastCard = null;
        ArrayList<AbstractCard> cardsPlayed = AbstractDungeon.actionManager.cardsPlayedThisTurn;
        if (!cardsPlayed.isEmpty()) {
            lastCard = cardsPlayed.get(cardsPlayed.size() - 1);
        }
        if (lastCard == null) {
            this.rawDescription = DESCRIPTION;
        } else {
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0] + lastCard.name + EXTENDED_DESCRIPTION[1];
        }
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

    @Override
    public AbstractDynamicCard makeCopy() {
        return new DejaVu();
    }
}
