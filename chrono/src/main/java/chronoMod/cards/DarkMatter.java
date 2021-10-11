package chronoMod.cards;

import chronoMod.ChronoMod;
import chronoMod.actions.GainJadeAction;
import chronoMod.characters.Chronomancer;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static chronoMod.ChronoMod.makeCardPath;

public class DarkMatter extends AbstractDynamicCard {
    private static final String CARD_ID = DarkMatter.class.getSimpleName();
    public static final String ID = ChronoMod.makeID(CARD_ID);
    public static final String IMG = makeCardPath("skill/" + CARD_ID + ".png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Chronomancer.Enums.COLOR_BLUE;

    private static final int COST = 0;
    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    public DarkMatter() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, this.block));
        this.addToBot(new GainJadeAction(1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkMatter();
    }
}
